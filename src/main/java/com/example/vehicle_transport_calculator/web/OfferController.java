package com.example.vehicle_transport_calculator.web;


import com.example.vehicle_transport_calculator.model.dto.AddOfferDTO;
import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;
import com.example.vehicle_transport_calculator.service.ExRateService;
import com.example.vehicle_transport_calculator.service.OfferService;
import com.example.vehicle_transport_calculator.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offers")
public class OfferController {

  private final OfferService offerService;
  private final ExRateService exRateService;

  public OfferController(OfferService offerService, ExRateService exRateService) {
    this.offerService = offerService;
      this.exRateService = exRateService;
  }

  @ModelAttribute("allEngineTypes")
  public EngineTypeEnum[] allEngineTypes() {
    return EngineTypeEnum.values();
  }

  @ModelAttribute("allPortsOfLoading")
  public PortOfLoadingEnum[] portOfLoading() {
    return PortOfLoadingEnum.values();
  }

  @ModelAttribute("allPortsOfDischarge")
  public PortOfDischargeEnum[] portOfDischarge() {
    return PortOfDischargeEnum.values();
  }


  @GetMapping("/add")
  public String newOffer(Model model) {

    if (!model.containsAttribute("addOfferDTO")) {
      model.addAttribute("addOfferDTO", AddOfferDTO.empty());
    }

    return "offer-add";
  }

  @PostMapping("add")
  public String createOffer(
      @Valid AddOfferDTO addOfferDTO,
      BindingResult bindingResult,
      RedirectAttributes rAtt) {

    if(bindingResult.hasErrors()){
      rAtt.addFlashAttribute("addOfferDTO", addOfferDTO);
      rAtt.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDTO", bindingResult);
      return "redirect:/offers/add";
    }


    offerService.createOffer(addOfferDTO);

    return "redirect:/offers/all";
  }

  @GetMapping("/{id}")
  public String offerDetails(@PathVariable("id") Long id,
      Model model) {

    model.addAttribute("offerDetails", offerService.getOfferDetails(id));
    model.addAttribute("allCurrencies", exRateService.allSupportedCurrencies());

    return "details";
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFound(ObjectNotFoundException onfe) {
    ModelAndView modelAndView = new ModelAndView("offer-not-found");
    modelAndView.addObject("offerId", onfe.getId());

    return modelAndView;
  }

  @DeleteMapping("/{id}")
  public String deleteOffer(@PathVariable("id") Long id) {

    offerService.deleteOffer(id);

    return "redirect:/offers/all";
  }
}
