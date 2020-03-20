/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.controller;

import com.udea.entity.Customer;
import com.udea.entity.DiscountCode;
import com.udea.entity.MicroMarket;
import com.udea.session.DiscountCodeManagerLocal;
import com.udea.session.ZipCodeManagerLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

/**
 *
 * @author arcangelmarinp
 */
public class CustomerMBean implements Serializable {

    @EJB
    private ZipCodeManagerLocal zipCodeManager;

    @EJB
    private DiscountCodeManagerLocal discountCodeManager;

    @EJB
    private com.udea.session.CustomerManagerLocal customerManager;

    /**
     * Creates a new instance of CustomerMBean
     */
    //Propiedades del modelo
    private Customer customer; //para mostrar, actualizar o insertar en el formulario
    private List<Customer> customers; //para visualizar en la tabla

    public CustomerMBean() {
    }
//retorna una lista de clientes para mostrar en un datatable de JSF

    public List<Customer> getCustomers() {
        if ((customers == null) || (customers.isEmpty())) {
            refresh();
        }
        return customers;
    }
//Retorna el detalle de cada cliente en el formulario.

    public Customer getDetails() {
        return customer;
    }

    /**
     * Action handler - llamado cuando en una fila de la tabla se haga click en
     * ID
     *
     * @param customer
     * @return
     */

    public String showDetails(Customer customer) {
        this.customer = customer;
        return "DETAILS"; // Permite enlazar a CustomerDetails.xml
    }

    /**
     * Action handler - Actualiza el modelo Customer en la BD. Se llama cuando
     * se presiona el boton update del formulario
     *
     * @return
     */
    public String update() {
        System.out.println("###UPDATE###");
        customer = customerManager.update(customer);
        return "SAVED"; // Para el caso navegacional
    }

    /**
     * Action handler - returno hacia la vista de la lista de clientes
     *
     * @return
     */
    public String list() {
        System.out.println("###LIST###");
        return "LIST";
    }

    private void refresh() {
        customers = customerManager.getAllCustomers();
    }
//Para cargar correctamente los combobox del formulario Details

    public javax.faces.model.SelectItem[] getDiscountCodes() {
        SelectItem[] options = null;
        List<DiscountCode> discountCodes = discountCodeManager.getDiscountCodes();
        if (discountCodes != null && discountCodes.size() > 0) {
            int i = 0;
            options = new SelectItem[discountCodes.size()];
            for (DiscountCode dc : discountCodes) {
                options[i++] = new SelectItem(dc.getDiscountCode(),
                        dc.getDiscountCode() + " (" + dc.getRate() + "%)");
            }
        }
        return options;
    }

    public javax.faces.model.SelectItem[] getZipCodes() {
        SelectItem[] options = null;
        List<MicroMarket> zipCodes = zipCodeManager.getZipCodes();
        if (zipCodes != null && zipCodes.size() > 0) {
            int i = 0;
            options = new SelectItem[zipCodes.size()];
            for (MicroMarket dc : zipCodes) {
                options[i++] = new SelectItem(dc.getZipCode(), dc.getZipCode());
            }
        }
        return options;

    }
    
}
