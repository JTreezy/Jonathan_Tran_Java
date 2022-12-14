package com.trilogyed.gamestoreinvoicing.service;

import com.trilogyed.gamestoreinvoicing.model.*;
import com.trilogyed.gamestoreinvoicing.repository.InvoiceRepository;
import com.trilogyed.gamestoreinvoicing.repository.ProcessingFeeRepository;
import com.trilogyed.gamestoreinvoicing.repository.TaxRepository;
import com.trilogyed.gamestoreinvoicing.util.feign.CatalogClient;
import com.trilogyed.gamestoreinvoicing.viewModel.ConsoleViewModel;
import com.trilogyed.gamestoreinvoicing.viewModel.GameViewModel;
import com.trilogyed.gamestoreinvoicing.viewModel.InvoiceViewModel;
import com.trilogyed.gamestoreinvoicing.viewModel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InvoicingServiceLayer {

    private final BigDecimal PROCESSING_FEE = new BigDecimal("15.49");
    private final BigDecimal MAX_INVOICE_TOTAL = new BigDecimal("999.99");
    private final String GAME_ITEM_TYPE = "Game";
    private final String CONSOLE_ITEM_TYPE = "Console";
    private final String TSHIRT_ITEM_TYPE = "T-Shirt";

    CatalogClient client;
    InvoiceRepository invoiceRepo;
    TaxRepository taxRepository;
    ProcessingFeeRepository processingFeeRepository;

    @Autowired
    public InvoicingServiceLayer(CatalogClient client, InvoiceRepository invoiceRepo, TaxRepository taxRepository, ProcessingFeeRepository processingFeeRepository) {
        this.client = client;
        this.invoiceRepo = invoiceRepo;
        this.taxRepository = taxRepository;
        this.processingFeeRepository = processingFeeRepository;
    }

    public InvoiceViewModel createInvoice(InvoiceViewModel invoiceViewModel) {

        //validation...
        if (invoiceViewModel==null)
            throw new NullPointerException("Create invoice failed. no invoice data.");

        if(invoiceViewModel.getItemType()==null)
            throw new IllegalArgumentException("Unrecognized Item type. Valid ones: Console or Game");

        //Check Quantity is > 0...
        if(invoiceViewModel.getQuantity()<=0){
            throw new IllegalArgumentException(invoiceViewModel.getQuantity() +
                    ": Unrecognized Quantity. Must be > 0.");
        }

        //start building invoice...
        Invoice invoice = new Invoice();
        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipcode(invoiceViewModel.getZipcode());
        invoice.setItemType(invoiceViewModel.getItemType());
        invoice.setItemId(invoiceViewModel.getItemId());

        //Checks the item type and get the correct unit price
        //Check if we have enough quantity
        if (invoiceViewModel.getItemType().equals(CONSOLE_ITEM_TYPE)) {

            Console returnVal = client.getConsoleById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            }

            if (invoiceViewModel.getQuantity()> returnVal.getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }

            invoice.setUnitPrice(returnVal.getPrice());

        } else if (invoiceViewModel.getItemType().equals(GAME_ITEM_TYPE)) {

            Game returnVal = client.getGameById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            }

            if(invoiceViewModel.getQuantity() >  returnVal.getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }
            invoice.setUnitPrice(returnVal.getPrice());

        } else if (invoiceViewModel.getItemType().equals(TSHIRT_ITEM_TYPE)) {
            TShirt returnVal = client.getTShirtById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            }

            if(invoiceViewModel.getQuantity() >  returnVal .getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }
            invoice.setUnitPrice(returnVal .getPrice());

        } else {
            throw new IllegalArgumentException(invoiceViewModel.getItemType()+
                    ": Unrecognized Item type. Valid ones: T-Shirt, Console, or Game");
        }

        invoice.setQuantity(invoiceViewModel.getQuantity());

        invoice.setSubtotal(
                invoice.getUnitPrice().multiply(
                        new BigDecimal(invoiceViewModel.getQuantity())).setScale(2, RoundingMode.HALF_UP));

        if ((invoice.getSubtotal().compareTo(new BigDecimal(999.99)) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        String stateS = invoice.getState();
        //Validate State and Calc tax...
        BigDecimal tempTaxRate;
        Optional<Tax> returnVal = Optional.ofNullable(taxRepository.findByState(stateS));


        if (returnVal.isPresent()) {
            tempTaxRate = returnVal.get().getRate();
        } else {
            throw new IllegalArgumentException(invoice.getState() + ": Invalid State code.");
        }

        if (!tempTaxRate.equals(BigDecimal.ZERO))
            invoice.setTax(tempTaxRate.multiply(invoice.getSubtotal()));
        else
            throw new IllegalArgumentException( invoice.getState() + ": Invalid State code.");

        BigDecimal processingFee;
        String productType = invoice.getItemType();
        System.out.println(productType);
        Optional<ProcessingFee> returnVal2 = Optional.ofNullable(processingFeeRepository.findByProductType(productType));

        if (returnVal2.isPresent()) {
            processingFee = returnVal2.get().getFee();
            System.out.println(returnVal2);
        } else {
            throw new IllegalArgumentException("Requested item is unavailable.");
        }

        invoice.setProcessingFee(processingFee);

        //Checks if quantity of items if greater than 10 and adds additional processing fee
        if (invoiceViewModel.getQuantity() > 10) {
            invoice.setProcessingFee(invoice.getProcessingFee().add(PROCESSING_FEE));
        }

        invoice.setTotal(invoice.getSubtotal().add(invoice.getProcessingFee()).add(invoice.getTax()));

        //checks total for validation
        if ((invoice.getTotal().compareTo(MAX_INVOICE_TOTAL) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        invoice = invoiceRepo.save(invoice);

        return buildInvoiceViewModel(invoice);
    }

    public InvoiceViewModel getInvoice(long id) {
        Optional<Invoice> invoice = invoiceRepo.findById(id);
        if (invoice == null)
            return null;
        else
            return buildInvoiceViewModel(invoice.get());
    }

    public List<InvoiceViewModel> getAllInvoices() {
        List<Invoice> invoiceList = invoiceRepo.findAll();
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> {
                ivmList.add(buildInvoiceViewModel(i));
            });
        }
        return ivmList;
    }

    public List<InvoiceViewModel> getInvoicesByCustomerName(String name) {
        List<Invoice> invoiceList = invoiceRepo.findByName(name);
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> ivmList.add(buildInvoiceViewModel(i)));
        }
        return ivmList;
    }

    public void deleteInvoice(long id){
        invoiceRepo.deleteById(id);
    }

    //Game service layer...
    public GameViewModel createGame(GameViewModel gameViewModel) {

        // Validate incoming Game Data in the view model.
        // All validations were done using JSR303
        if (gameViewModel==null) throw new IllegalArgumentException("No Game is passed! Game object is null!");

        Game game = new Game();
        game.setTitle(gameViewModel.getTitle());
        game.setEsrbRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setQuantity(gameViewModel.getQuantity());
        game.setStudio(gameViewModel.getStudio());

        gameViewModel.setId(client.saveGame(game).getId());
        return gameViewModel;
    }

    public GameViewModel getGame(long id) {
        Game game = client.getGameById(id);
        if (game == null)
            return null;
        else
            return buildGameViewModel(game);
    }

    public void updateGame(GameViewModel gameViewModel) {

        //Validate incoming Game Data in the view model
        if (gameViewModel==null)
            throw new IllegalArgumentException("No Game data is passed! Game object is null!");

        //make sure the game exists. and if not, throw exception...
        if (this.getGame(gameViewModel.getId())==null)
            throw new IllegalArgumentException("No such game to update.");

        Game game = new Game();
        game.setId(gameViewModel.getId());
        game.setTitle(gameViewModel.getTitle());
        game.setEsrbRating(gameViewModel.getEsrbRating());
        game.setDescription(gameViewModel.getDescription());
        game.setPrice(gameViewModel.getPrice());
        game.setQuantity(gameViewModel.getQuantity());
        game.setStudio(gameViewModel.getStudio());

        client.saveGame(game);
    }

    public void deleteGame(long id) {
        client.deleteGameById(id);
    }

    public List<GameViewModel> getGameByEsrb(String esrb) {
        List<Game> gameList = client.selectGameByEsrbRating(esrb);
        List<GameViewModel> gvmList = new ArrayList<>();
        if (gameList == null)
            return null;
        else
            gameList.stream().forEach(g -> gvmList.add(buildGameViewModel(g)));
        return gvmList;
    }

    public List<GameViewModel> getGameByTitle(String title) {
        List<Game> gameList = client.selectGameByTitle(title);
        List<GameViewModel> gvmList = new ArrayList<>();
        List<GameViewModel> exceptionList = null;

        if (gameList == null) {
            return exceptionList;
        } else {
            gameList.stream().forEach(g -> gvmList.add(buildGameViewModel(g)));
        }
        return gvmList;
    }

    public List<GameViewModel> getGameByStudio(String studio) {
        List<Game> gameList = client.selectGameByStudio(studio);
        List<GameViewModel> gvmList = new ArrayList<>();

        if (gameList == null)
            return null;
        else
            gameList.stream().forEach(g -> gvmList.add(buildGameViewModel(g)));
        return gvmList;
    }

    public List<GameViewModel> getAllGames() {
        List<Game> gameList = client.getAllGames();
        List<GameViewModel> gvmList = new ArrayList<>();

        if (gameList == null)
            return null;
        else
            gameList.stream().forEach(g -> gvmList.add(buildGameViewModel(g)));
        return gvmList;
    }

    //CONSOLE SERVICE LAYER METHODS...
    public ConsoleViewModel createConsole(ConsoleViewModel consoleViewModel) {

        // Remember viewModel data was validated using JSR 303
        // Validate incoming Console Data in the view model
        if (consoleViewModel==null) throw new IllegalArgumentException("No Console is passed! Game object is null!");

        Console console = new Console();
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());

        return buildConsoleViewModel(client.saveConsole(console));
    }

    public ConsoleViewModel getConsole(long id) {
        Console console = client.getConsoleById(id);
        if (console == null)
            return null;
        else
            return buildConsoleViewModel(console);
    }

    public void updateConsole(ConsoleViewModel consoleViewModel) {

        //Validate incoming Console Data in the view model
        if (consoleViewModel==null)
            throw new IllegalArgumentException("No console data is passed! Console object is null!");

        //make sure the Console exists. and if not, throw exception...
        if (client.getConsoleById(consoleViewModel.getId()) == null)
            throw new IllegalArgumentException("No such console to update.");

        Console console = new Console();
        console.setId(consoleViewModel.getId());
        console.setModel(consoleViewModel.getModel());
        console.setManufacturer(consoleViewModel.getManufacturer());
        console.setMemoryAmount(consoleViewModel.getMemoryAmount());
        console.setProcessor(consoleViewModel.getProcessor());
        console.setPrice(consoleViewModel.getPrice());
        console.setQuantity(consoleViewModel.getQuantity());

        client.saveConsole(console);
    }

    public void deleteConsole(long id) {
        client.deleteConsoleById(id);
    }

    public List<ConsoleViewModel> getConsoleByManufacturer(String manufacturer) {
        List<Console> consoleList = client.selectConsoleByManufacturer(manufacturer);
        List<ConsoleViewModel> cvmList = new ArrayList<>();

        if (consoleList == null)
            return null;
        else
            consoleList.stream().forEach(c -> cvmList.add(buildConsoleViewModel(c)));
        return cvmList;
    }

    public List<ConsoleViewModel> getAllConsoles() {
        List<Console> consoleList = client.getAllConsoles();
        List<ConsoleViewModel> cvmList = new ArrayList<>();

        if (consoleList == null)
            return null;
        else
            consoleList.stream().forEach(c -> cvmList.add(buildConsoleViewModel(c)));
        return cvmList;
    }

    //TSHIRT SERVICE LAYER

    public TShirtViewModel createTShirt(TShirtViewModel tShirtViewModel) {

        // Remember model view has already been validated through JSR 303
        // Validate incoming TShirt Data in the view model
        if (tShirtViewModel==null) throw new IllegalArgumentException("No TShirt is passed! TShirt object is null!");

        TShirt tShirt = new TShirt();
        tShirt.setSize(tShirtViewModel.getSize());
        tShirt.setColor(tShirtViewModel.getColor());
        tShirt.setDescription(tShirtViewModel.getDescription());
        tShirt.setPrice(tShirtViewModel.getPrice());
        tShirt.setQuantity(tShirtViewModel.getQuantity());

        tShirt = client.saveTShirt(tShirt);

        return buildTShirtViewModel(tShirt);
    }

    public TShirtViewModel getTShirt(long id) {
        TShirt tShirt = client.getTShirtById(id);
        if (tShirt == null)
            return null;
        else
            return buildTShirtViewModel(tShirt);
    }

    public void updateTShirt(TShirtViewModel tShirtViewModel) {

        // Remember model view has already been validated through JSR 303
        // Validate incoming TShirt Data in the view model
        if (tShirtViewModel==null) throw new IllegalArgumentException("No TShirt is passed! TShirt object is null!");

        //make sure the Console exists. and if not, throw exception...
        if (this.getTShirt(tShirtViewModel.getId())==null)
            throw new IllegalArgumentException("No such TShirt to update.");

        TShirt tShirt = new TShirt();
        tShirt.setId(tShirtViewModel.getId());
        tShirt.setSize(tShirtViewModel.getSize());
        tShirt.setColor(tShirtViewModel.getColor());
        tShirt.setDescription(tShirtViewModel.getDescription());
        tShirt.setPrice(tShirtViewModel.getPrice());
        tShirt.setQuantity(tShirtViewModel.getQuantity());

        client.saveTShirt(tShirt);
    }

    public void deleteTShirt(long id) {

        client.deleteTShirtById(id);
    }

    public List<TShirtViewModel> getTShirtByColor(String color) {
        List<TShirt> tShirtList = client.selectTShirtByColour(color);
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    public List<TShirtViewModel> getTShirtBySize(String size) {
        List<TShirt> tShirtList = client.selectTShirtBySize(size);
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    public List<TShirtViewModel> getAllTShirts() {
        List<TShirt> tShirtList = client.getAllTShirts();
        List<TShirtViewModel> tvmList = new ArrayList<>();

        if (tShirtList == null)
            return null;
        else
            tShirtList.stream().forEach(t -> tvmList.add(buildTShirtViewModel(t)));
        return tvmList;
    }

    //Helper Methods...

    public ConsoleViewModel buildConsoleViewModel(Console console) {
        ConsoleViewModel consoleViewModel = new ConsoleViewModel();
        consoleViewModel.setId(console.getId());
        consoleViewModel.setModel(console.getModel());
        consoleViewModel.setManufacturer(console.getManufacturer());
        consoleViewModel.setMemoryAmount(console.getMemoryAmount());
        consoleViewModel.setProcessor(console.getProcessor());
        consoleViewModel.setPrice(console.getPrice());
        consoleViewModel.setQuantity(console.getQuantity());

        return consoleViewModel;
    }

    public GameViewModel buildGameViewModel(Game game) {

        GameViewModel gameViewModel = new GameViewModel();
        gameViewModel.setId(game.getId());
        gameViewModel.setTitle(game.getTitle());
        gameViewModel.setEsrbRating(game.getEsrbRating());
        gameViewModel.setDescription(game.getDescription());
        gameViewModel.setPrice(game.getPrice());
        gameViewModel.setStudio(game.getStudio());
        gameViewModel.setQuantity(game.getQuantity());

        return gameViewModel;
    }

    public TShirtViewModel buildTShirtViewModel(TShirt tShirt) {
        TShirtViewModel tShirtViewModel = new TShirtViewModel();
        tShirtViewModel.setId(tShirt.getId());
        tShirtViewModel.setSize(tShirt.getSize());
        tShirtViewModel.setColor(tShirt.getColor());
        tShirtViewModel.setDescription(tShirt.getDescription());
        tShirtViewModel.setPrice(tShirt.getPrice());
        tShirtViewModel.setQuantity(tShirt.getQuantity());

        return tShirtViewModel;
    }

    public InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setId(invoice.getId());
        invoiceViewModel.setName(invoice.getName());
        invoiceViewModel.setStreet(invoice.getStreet());
        invoiceViewModel.setCity(invoice.getCity());
        invoiceViewModel.setState(invoice.getState());
        invoiceViewModel.setZipcode(invoice.getZipcode());
        invoiceViewModel.setItemType(invoice.getItemType());
        invoiceViewModel.setItemId(invoice.getItemId());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setQuantity(invoice.getQuantity());
        invoiceViewModel.setSubtotal(invoice.getSubtotal());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTotal(invoice.getTotal());

        return invoiceViewModel;
    }
}
