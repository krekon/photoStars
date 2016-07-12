package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import paypal.PayPalSucess;

@Controller
@RequestMapping(value="paypal")
public class PaypalController {
	
	@RequestMapping(method= RequestMethod.GET)
	public String index()
	{
		return "index";
	}
	
	@RequestMapping(value="/success",method = RequestMethod.GET)
	public String success(ModelMap modelMap, HttpServletRequest request)
	{
		PayPalSucess payPalSuccess = new PayPalSucess();
		modelMap.put("result", payPalSuccess.getPayPal(request));
		return "success";
	}

}
