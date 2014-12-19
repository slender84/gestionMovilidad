/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

import entities.Equivalencia;



public class EquivalenciaRevisada {

   private Equivalencia equivalencia;
    private boolean igual;
    
    public EquivalenciaRevisada(Equivalencia equivalencia){
        
        this.equivalencia=equivalencia;
        
    }

    public Equivalencia getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(Equivalencia equivalencia) {
        this.equivalencia = equivalencia;
    }

    public boolean isIgual() {
        return igual;
    }

    public void setIgual(boolean igual) {
        this.igual = igual;
    }
    
    
    
    
    
}
