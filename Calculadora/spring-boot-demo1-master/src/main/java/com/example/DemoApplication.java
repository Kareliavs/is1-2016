package com.example;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@SpringBootApplication
public class DemoApplication 
{
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Usado el dia " + new Date()+"              Ingrese a /hello&name=SuNombre para mas informacion";
    }

    @RequestMapping("/hello")
    @ResponseBody
    String hello(@RequestParam String name) {
        return "Hola " + name +","
        		+" puede operar con:  mas , - , * , /  "
        		+" ingrese en la forma:  '/operar?a=30&b=5&op=mas'";
    }
    
    Boolean Error= false;
    @RequestMapping("/operar")
    @ResponseBody
    String Operar(@RequestParam String a,@RequestParam String b,@RequestParam String op) {
    	String resultado = Interfaz(a, b, op);
    	if (Error) 
    	{	Error=false; 
    		return "OPERACION INVALIDA"+" Verifique que 'a' y 'b' sean numeros "+" y/o que 'op' sea una operación valida (mas,-,*,/)";
    	}
    	else      
        {   Error=false; 
    		return "Operando:  "+a+" "+op+" "+b+" = "+resultado;
        }
    	
    }
    
    boolean isNumeric(String a){
    	try {
    		Integer.parseInt(a);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    Integer StringAInteger(String a)
    {
    	if(isNumeric(a)) 
    	{
	    	int numa=0;
	    	numa=Integer.parseInt(a);
	    	return numa;
    	}
    	else 
    	{
    		Error=true;
    		return 1; 
    	}
    	
    }
    String IntegerAString(Integer a)
    {
    	String Resul= "";
        Resul=String.valueOf(a);
        return Resul;
    }
    
    
//Dependency Inversion Principle 
    String Interfaz(String a, String b, String op)
    {
    	int numa=0;
        int numb=0;
    	numa=StringAInteger(a);
        numb=StringAInteger(b);
    	return IntegerAString(Elegir(numa,numb,op));
    }
    
    Integer Elegir(Integer a, Integer b, String op)
    {   //return Suma(a,b);
    	if(op.equals(new String("mas")))
			return Sumar(a,b);
    	else if(op.equals(new String("-")))
			return Restar(a,b);
    	else if(op.equals(new String("*")))
    		return Multiplicar(a,b);
    	else if(op.equals(new String("/")))
    	    return Dividir(a,b);
    	else 
    		{
    		 Error=true;
    		 return 1;
    		}
    }
   
//Single Responsibility Principle
//Domain services should have a verb in the class name
	private Integer Sumar(Integer a, Integer b) {
		return a+b;
	}
	private Integer Restar(Integer a, Integer b) {
		return a-b;
	}
	private Integer Multiplicar(Integer a, Integer b) {
		return a*b;
	}
	private Integer Dividir(Integer a, Integer b) {
		return a/b;
	}

	public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
    }
}
