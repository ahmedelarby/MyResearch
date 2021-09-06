package com.myresearch.myresearch;

public class DataModel {
    String request_date;
   String received_date;
   String password;
   String subject;

   String price ;
   String case_request;

   public DataModel() {
   }

   public DataModel(String request_date, String received_date, String password, String subject,  String price, String case_request) {
      this.request_date = request_date;
      this.received_date = received_date;
      this.password = password;
      this.subject = subject;

      this.price = price;
      this.case_request = case_request;
   }

   public String getRequest_date() {
      return request_date;
   }

   public void setRequest_date(String request_date) {
      this.request_date = request_date;
   }

   public String getReceived_date() {
      return received_date;
   }

   public void setReceived_date(String received_date) {
      this.received_date = received_date;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }



   public String getPrice() {
      return price;
   }

   public void setPrice(String price) {
      this.price = price;
   }

   public String getCase_request() {
      return case_request;
   }

   public void setCase_request(String case_request) {
      this.case_request = case_request;
   }
}
