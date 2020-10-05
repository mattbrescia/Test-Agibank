package com.agibank.agibank.model;

import java.util.ArrayList;
import java.util.List;

public class Sell {

    private String id;
    private List<Item> itens;
    private String salesmanName;
    private String bestSalesmanName;
    private String worstSalesmanName;

    private Double totalVenda = 0d;

    public Sell(){

    }

    public Sell(String id, String itensSplit, String salesmanName) {
        this.id = id;
        this.salesmanName = salesmanName;
        this.itens = parseItens(itensSplit);
    }


    private List<Item> parseItens(String itensSplit) {
        itensSplit = itensSplit.replace("\\]", "").replace("\\[", "");

        String[] itemSplit = itensSplit.split(",");
        List<Item> itens = new ArrayList<Item>();

        for (String itemStr : itemSplit) {
            String[] split = itemStr.split("-");
            Item item = new Item(split[0], Integer.valueOf(split[1]), Double.valueOf(split[2]));
            itens.add(item);

            // faz um totalizador da venda
            totalVenda += (item.getPrice() * item.getQuantity());
        }

        return itens;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItens() {
        return itens;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public String getWorstSeller() {
        return this.worstSalesmanName;
    }

    public String getBestSeller() {
        return this.bestSalesmanName;
    }

    public Double getTotalSell() {
        return totalVenda;
    }

    public void setBestSalesmanName(String name){
        this.bestSalesmanName = name;
    }

    public void setWorstSalesmanName(String name){
        this.worstSalesmanName = name;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

}
