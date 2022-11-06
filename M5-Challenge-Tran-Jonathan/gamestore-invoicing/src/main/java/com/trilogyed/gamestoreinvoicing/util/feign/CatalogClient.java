package com.trilogyed.gamestoreinvoicing.util.feign;

import com.trilogyed.gamestoreinvoicing.model.Console;
import com.trilogyed.gamestoreinvoicing.model.Game;
import com.trilogyed.gamestoreinvoicing.model.TShirt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.Path;
import java.util.List;

@FeignClient(name="gamestore-catalog")
public interface CatalogClient {
    @RequestMapping(value = "/console/{id}", method = RequestMethod.GET)
    public Console getConsoleById(@PathVariable long id);

    @RequestMapping(value = "/console", method = RequestMethod.POST)
    public Console saveConsole(@RequestBody Console console);

    @RequestMapping(value = "/console/{id}", method = RequestMethod.DELETE)
    public Console deleteConsoleById(@PathVariable long id);

    @RequestMapping(value = "/console/manufacturer/{manufacturer}", method = RequestMethod.GET)
    public List<Console> selectConsoleByManufacturer(@PathVariable String esrbRating);


    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public List<Console> getAllConsoles();



    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    public Game getGameById(@PathVariable long id);

    @RequestMapping(value = "/game", method = RequestMethod.POST)
    public Game saveGame(@RequestBody Game game);

    @RequestMapping(value = "/game/{id}", method = RequestMethod.DELETE)
    public Game deleteGameById(@PathVariable long id);

    @RequestMapping(value = "/game/esrbRating/{esrbRating}", method = RequestMethod.GET)
    public List<Game> selectGameByEsrbRating(@PathVariable String esrbRating);

    @RequestMapping(value = "/game/title/{title}", method = RequestMethod.GET)
    public List<Game> selectGameByTitle(@PathVariable String title);

    @RequestMapping(value = "/game/studio/{studio}", method = RequestMethod.GET)
    public List<Game> selectGameByStudio(@PathVariable String studio);

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public List<Game> getAllGames();


    @RequestMapping(value = "/tshirt/{id}", method = RequestMethod.GET)
    public TShirt getTShirtById(@PathVariable long id);
    @RequestMapping(value = "/tshirt", method = RequestMethod.POST)
    public TShirt saveTShirt(@RequestBody TShirt tshirt);

    @RequestMapping(value = "/tshirt/{id}", method = RequestMethod.DELETE)
    public TShirt deleteTShirtById(@PathVariable long id);

    @RequestMapping(value = "/tshirt/color/{color}", method = RequestMethod.GET)
    public List<TShirt> selectTShirtByColour(@PathVariable String esrbRating);

    @RequestMapping(value = "/size/{size}", method = RequestMethod.GET)
    public List<TShirt> selectTShirtBySize(@PathVariable String title);

    @RequestMapping(value = "/tshirt", method = RequestMethod.GET)
    public List<TShirt> getAllTShirts();

}
