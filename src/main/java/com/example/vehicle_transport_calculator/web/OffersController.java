package com.example.vehicle_transport_calculator.web;


import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;
import com.example.vehicle_transport_calculator.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OffersController {

  private final OfferService offerService;

  public OffersController(OfferService offerService) {
    this.offerService = offerService;
  }

  @ModelAttribute("oceanFreight")
  public PortOfLoadingEnum[] portOfLoading() {
    return PortOfLoadingEnum.values();
  }


  @GetMapping("/all")
  public String getAllOffers(Model model) {

    model.addAttribute("allOffers", offerService.getAllOffersSummary());

    return "offers";
  }

}
