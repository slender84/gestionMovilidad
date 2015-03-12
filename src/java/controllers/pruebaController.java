/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import entities.Asignatura;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.EquivalenciaService;




@ManagedBean
@ViewScoped
public class pruebaController {

    
    @ManagedProperty(value="#{equivalenciaService}")
    private EquivalenciaService equivalenciaService;
    
    private ArrayList<Object> lista;
    private String valor="dfsdf";
    
    public pruebaController() {
    }

    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }

    public ArrayList<Object> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Object> lista) {
        this.lista = lista;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
    @PostConstruct
    public void init(){
        
        
        lista=(ArrayList<Object>)equivalenciaService.listarAsignaturasMovilidad();
        
        System.out.println(lista.get(0).getClass().getName());
        
        Object[] arrayObject=(Object[])lista.get(0);
        
        System.out.println(arrayObject[1]);
        
        ArrayList<Asignatura> lista2=(ArrayList < Asignatura >)equivalenciaService.lista2();
        
        System.out.println(lista2.get(0).getNombreAsignatura());
        
        
    }
    
    
    
    
    
    
    
}
