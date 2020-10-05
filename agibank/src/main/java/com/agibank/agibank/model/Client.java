package com.agibank.agibank.model;

public class Client {
        private String cnpj;
        private String name;
        private String bussiness;
    
        public Client(String cnpj, String name, String bussiness) {
            this.cnpj = cnpj;
            this.name = name;
            this.bussiness = bussiness;
        }
    
        public String getCnpj() {
            return cnpj;
        }
    
        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getBussiness() {
            return bussiness;
        }
    
        public void setBussiness(String bussiness) {
            this.bussiness = bussiness;
        }
    
        @Override
        public String toString() {
            return "Client [cnpj=" + cnpj + ", name=" + name + ", bussiness=" + bussiness + "]";
        }
    
    
    
}
    