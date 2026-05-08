package de.giuseppe.ghostnet.controller;

import de.giuseppe.ghostnet.form.BergenForm;
import de.giuseppe.ghostnet.form.GeisternetzForm;
import de.giuseppe.ghostnet.form.VerschollenForm;
import de.giuseppe.ghostnet.model.Geisternetz;
import de.giuseppe.ghostnet.service.GeisternetzService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GeisternetzController {

    private final GeisternetzService geisternetzService;

    @Autowired
    public GeisternetzController(GeisternetzService geisternetzService) {
        this.geisternetzService = geisternetzService;
    }

    @GetMapping("/")
    public String startseite() {
        return "start";
    }

    // mit ?filter=offen werden nur die noch zu bergenden Netze angezeigt
    @GetMapping("/netze")
    public String liste(@RequestParam(name = "filter", required = false) String filter, Model model) {
        List<Geisternetz> netze;
        if (filter != null && filter.equals("offen")) {
            netze = geisternetzService.nochZuBergendeNetze();
        } else {
            netze = geisternetzService.alleNetze();
        }
        model.addAttribute("netze", netze);
        model.addAttribute("filter", filter);
        return "liste";
    }

    @GetMapping("/netze/neu")
    public String formularNeu(Model model) {
        model.addAttribute("geisternetzForm", new GeisternetzForm());
        return "formular-neu";
    }

    @PostMapping("/netze/neu")
    public String netzAnlegen(@Valid @ModelAttribute("geisternetzForm") GeisternetzForm form,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formular-neu";
        }
        geisternetzService.netzMelden(
                form.getBreitengrad(),
                form.getLaengengrad(),
                form.getGeschaetzteGroesseKg(),
                form.getName(),
                form.getTelefonnummer()
        );
        return "redirect:/netze";
    }

    @GetMapping("/netze/{id}/bergen")
    public String formularBergen(@PathVariable("id") Long id, Model model) {
        Geisternetz netz = geisternetzService.findeNetz(id);
        model.addAttribute("netz", netz);
        model.addAttribute("bergenForm", new BergenForm());
        return "formular-bergen";
    }

    @PostMapping("/netze/{id}/bergen")
    public String fuerBergungEintragen(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("bergenForm") BergenForm form,
                                       BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            // Netz nochmal laden, damit die Info-Box im Template angezeigt werden kann
            Geisternetz netz = geisternetzService.findeNetz(id);
            model.addAttribute("netz", netz);
            return "formular-bergen";
        }
        geisternetzService.personFuerBergungEintragen(id, form.getName(), form.getTelefonnummer());
        return "redirect:/netze";
    }

    @PostMapping("/netze/{id}/geborgen")
    public String alsGeborgen(@PathVariable("id") Long id) {
        geisternetzService.alsGeborgenMelden(id);
        return "redirect:/netze";
    }

    @GetMapping("/netze/{id}/verschollen")
    public String formularVerschollen(@PathVariable("id") Long id, Model model) {
        Geisternetz netz = geisternetzService.findeNetz(id);
        model.addAttribute("netz", netz);
        model.addAttribute("verschollenForm", new VerschollenForm());
        return "formular-verschollen";
    }

    @PostMapping("/netze/{id}/verschollen")
    public String alsVerschollen(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("verschollenForm") VerschollenForm form,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            Geisternetz netz = geisternetzService.findeNetz(id);
            model.addAttribute("netz", netz);
            return "formular-verschollen";
        }
        geisternetzService.alsVerschollenMelden(id, form.getName(), form.getTelefonnummer());
        return "redirect:/netze";
    }
}
