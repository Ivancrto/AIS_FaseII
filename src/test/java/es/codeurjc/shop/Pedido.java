package es.codeurjc.shop;

import java.util.Arrays;
import java.util.List;

public class Pedido {
	
	long idP;
	long idC;
	long cost;
	int getProductCost;
	int withdrawProduct;
	int reserveCredit;
	int notify;
	int save;
	String msg;
	int statusAPI;
	int valueID;
	List<String> messageAPI;

	public Pedido() {		
	}

	//Constructor 1 Pruebas unitarias con dobles
	public Pedido(long idC,long idP, long cost, int getProductCost, int withdrawProduct, int reserveCredit, int notify, int save) {
		this.idP = idP;
		this.idC = idC;
		this.cost = cost;
		this.getProductCost = getProductCost;
		this.withdrawProduct = withdrawProduct;
		this.reserveCredit = reserveCredit;
		this.notify = notify;
		this.save = save;
		this.msg="Purchase: customer=" + this.idC + ", product=" + this.idP;
	}
	
	
	//Contructor 2 parte (SISTEMA)
	public Pedido(long idC,long idP, String msg) {
		this.idP = idP;
		this.idC = idC;
		this.msg= msg;
	}
	

	//Contructor 3 parte (API)
	public Pedido(long idP, long idC, int statusAPI,String m, String messageAPI,int id) {
		this.idP = idP;
		this.idC = idC;
		this.statusAPI = statusAPI;
		this.valueID=id;
		this.messageAPI= Arrays.asList(m,messageAPI);


	}

	public long getIdP() {
		return idP;
	}

	public void setIdP(long idP) {
		this.idP = idP;
	}

	public long getIdC() {
		return idC;
	}

	public void setIdC(long idC) {
		this.idC = idC;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public int getGetProductCost() {
		return getProductCost;
	}

	public void setGetProductCost(int getProductCost) {
		this.getProductCost = getProductCost;
	}

	public int getWithdrawProduct() {
		return withdrawProduct;
	}

	public void setWithdrawProduct(int withdrawProduct) {
		this.withdrawProduct = withdrawProduct;
	}

	public int getReserveCredit() {
		return reserveCredit;
	}

	public void setReserveCredit(int reserveCredit) {
		this.reserveCredit = reserveCredit;
	}

	public int getNotify() {
		return notify;
	}

	public void setNotify(int notify) {
		this.notify = notify;
	}

	public int getSave() {
		return save;
	}

	public void setSave(int save) {
		this.save = save;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatusAPI() {
		return statusAPI;
	}

	public void setStatusAPI(int statusAPI) {
		this.statusAPI = statusAPI;
	}


	public List<String> getMessageAPI() {
		return messageAPI;
	}

	public void setMessageAPI(List<String> messageAPI) {
		this.messageAPI = messageAPI;
	}

	public int getValueID() {
		return valueID;
	}

	public void setValueID(int valueID) {
		this.valueID = valueID;
	}



	
}

	
	
