package com.agibank.agibank.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.agibank.agibank.utils.FilesNotFoundException;
import com.agibank.agibank.model.Sell;
import com.agibank.agibank.model.Seller;
import com.agibank.agibank.model.Client;
import com.agibank.agibank.model.Item;

import org.springframework.web.bind.annotation.RestController;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yaml.snakeyaml.util.ArrayUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SellController {
    private List<Seller> Sellers = new ArrayList<Seller>();
    private List<Sell> Sells = new ArrayList<Sell>();
    private List<Client> Clients = new ArrayList<Client>();
    public String WorstSeller;

    @RequestMapping(method = RequestMethod.GET, value = "/load")
    public void findBysell() throws FilesNotFoundException {
        leArquivo();
    }

    public void leArquivo() {
        
        String diretorio = "%HOMEPATH%/data/in";
        
        try {
            List<Path> arquivos = Files.walk(Paths.get(diretorio))
                                       .filter(p -> p.toString().endsWith(".dat"))
                                       .collect(Collectors.toList());
            for (Path path : arquivos) {
                String arquivo = new String(Files.readAllBytes(path));
                try (Scanner scan = new Scanner(arquivo)) {
                    while (scan.hasNextLine()) {
                        String[] linha = scan.nextLine().split("ç");
                        
                        if ("001".equals(linha[0])) {
                            processSeller(linha);
                        } else if ("002".equals(linha[0])) {
                            processClient(linha);
                        } else if ("003".equals(linha[0])) {
                            processSell(linha);
                        }
                        
                    }
                }
                orderListByExpensiveSell();
                determineWorstSeller(Sells);
                
                Sell Sell = new Sell();
                writeExitFile(Sellers.size(), Sells.get(0).getId(), Clients.size(), Sell.getWorstSeller());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String determineWorstSeller(List<Sell> Sells) {
        String BestSalesman = Sells.get(0).getSalesmanName();
        Integer count = 0;
        Integer countAnother = 0;
        Sell Sell = new Sell();
        for (Sell sell : Sells){
            if (BestSalesman.equals(sell.getSalesmanName())) {
                count = count + 1;
                Sell.setBestSalesmanName(BestSalesman);
            } else {
                countAnother = countAnother + 1;
                Sell.setWorstSalesmanName(sell.getSalesmanName());
            }
        }
        return count > countAnother++ ? Sell.getBestSeller() : Sell.getWorstSeller();
    }
    
    private void writeExitFile(Integer sellersSize, String valuableSell, Integer clientsSize, String worstSeller) {
        Path filePath = Paths.get("%HOMEPATH%/data/out", "out.done.dat");
 
        try
        {
            Files.writeString(filePath, sellersSize.toString(), StandardOpenOption.APPEND);
            Files.writeString(filePath, clientsSize.toString(), StandardOpenOption.APPEND);
            Files.writeString(filePath, valuableSell, StandardOpenOption.APPEND);
            Files.writeString(filePath, worstSeller, StandardOpenOption.APPEND);
            String content = Files.readString(filePath);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private void orderListByExpensiveSell() {
        Sells.sort(new Comparator<Sell>() {
            @Override
            public int compare(Sell o1, Sell o2) {
                return o1.getTotalSell() > o2.getTotalSell() ? 1 : 0;
            }
        });
    }
    
    private void processClient(String[] linha) {
        Client Client = new Client(linha[1], linha[2], linha[3]);
        Clients.add(Client);
    }
    
    private void processSell(String[] linha) {
        Sell Sell = new Sell(linha[1], linha[2], linha[3]);
        Sell.setSalesmanName(linha[3]);
        Sells.add(Sell);
    }
    
    
    private void processSeller(String[] linha) {
        Seller Seller = new Seller(linha[1], linha[2], Double.valueOf(linha[3]));
        Sellers.add(Seller);
    }
}
