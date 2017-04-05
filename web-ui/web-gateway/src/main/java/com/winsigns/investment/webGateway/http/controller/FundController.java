package com.winsigns.investment.webGateway.http.controller;

import com.winsigns.investment.webGateway.http.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/funds")
public class FundController {

	@Autowired
	FundService fundService;

	@GetMapping("/tree")
	public List<?> getFundTree(){
		return fundService.getFundTree();
	}
}
