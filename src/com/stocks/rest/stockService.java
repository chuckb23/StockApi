package com.stocks.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.financial.data.StockApplication;
import com.financial.data.StockDaily;

@Path("/stock")
public class stockService {
	  @GET
	  @Produces("application/json")
	  public Response convertFtoC() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	  }
	  @Path("{symbol}")
	  @GET
	  @Produces("application/json")
	  public Response putDailyStock(@PathParam("symbol") String symbol) throws JSONException {
		StockApplication sa = new StockApplication();
		StockDaily[] sd = sa.getDailyStock(symbol);
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < sd.length; i++){
			JSONObject jsonObject = new JSONObject();
			StockDaily stock = sd[i];
			jsonObject.put("StockSymbol", stock.getSymbol());
			jsonObject.put("Open", stock.getOpen());
			jsonObject.put("Close", stock.getClose());
			jsonObject.put("Date", stock.getTradingDay());
			jsonObject.put("PercentChange", stock.getPercentChange());
			jsonArray.put(jsonObject);
		}
		JSONObject main = new JSONObject();
		main.put("results", jsonArray);
		String result = main.toString();
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(result).build();
	  }
}
