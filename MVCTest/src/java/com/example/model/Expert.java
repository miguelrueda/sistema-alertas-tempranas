/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t41507
 */
public class Expert implements java.io.Serializable {
    
    private static final long serialVersionUID = -1L;
    
    public List getBrands(String color) {
        List brands = new ArrayList();
        if (color.equals("amber")) {
            brands.add("Jack Amber");
            brands.add("Red Moose");
        } else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }
    
}
