package com.company.RESTwebservice.controllers;

import com.company.RESTwebservice.exceptions.ArgumentIsNotANumberException;
import com.company.RESTwebservice.models.MathSolution;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

@RestController
public class MathSolutionController extends RuntimeException{

    @RequestMapping(value="/add", method= RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution add(@RequestBody MathSolution mathSolution) throws ArgumentIsNotANumberException {
        //fill up the json request body with the two operand properties
        //throw an error if one or both of them are not integers/not filled -> if statement?
        //how to throw 422 if missing operand or if operands are not both numbers
        try {
            mathSolution.setOperand1(mathSolution.getOperand1());
            mathSolution.setOperand2(mathSolution.getOperand2());
            mathSolution.setOperation("add");
            mathSolution.setAnswer(mathSolution.getOperand1() + mathSolution.getOperand2());

        } catch(NullPointerException e){
            throw new ArgumentIsNotANumberException("Both operands must be filled with an integer data type for the operation to work.");
        }
        return mathSolution;
    }

    @RequestMapping(value="/subtract", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution subtract(@RequestBody MathSolution mathSolution){
        try {
            mathSolution.setOperand1(mathSolution.getOperand1());
            mathSolution.setOperand2(mathSolution.getOperand2());
            mathSolution.setOperation("subtract");
            mathSolution.setAnswer(mathSolution.getOperand1() - mathSolution.getOperand2());

        } catch(NullPointerException e){
            throw new ArgumentIsNotANumberException("Both operands must be filled with an integer data type for the operation to work.");
        }
        return mathSolution;
    }

    @RequestMapping(value="/multiply", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution multiply(@RequestBody MathSolution mathSolution){
        try {
            mathSolution.setOperand1(mathSolution.getOperand1());
            mathSolution.setOperand2(mathSolution.getOperand2());
            mathSolution.setOperation("multiply");
            mathSolution.setAnswer(mathSolution.getOperand1() * mathSolution.getOperand2());

        } catch(NullPointerException e){
            throw new ArgumentIsNotANumberException("Both operands must be filled with an integer data type for the operation to work.");
        }
        return mathSolution;
    }

    @RequestMapping(value="/divide", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public MathSolution divide(@RequestBody MathSolution mathSolution){
        try {
            mathSolution.setOperand1(mathSolution.getOperand1());
            mathSolution.setOperand2(mathSolution.getOperand2());
            mathSolution.setOperation("divide");
            mathSolution.setAnswer(mathSolution.getOperand1() / mathSolution.getOperand2());

        } catch(NullPointerException e){
            throw new ArgumentIsNotANumberException("Both operands must be filled with an integer data type for the operation to work.");
        } catch(ArithmeticException e){
            throw new IllegalArgumentException("Why are you trying to divide by zero? Don't you know the world will end??");
        }
        return mathSolution;
    }



}
