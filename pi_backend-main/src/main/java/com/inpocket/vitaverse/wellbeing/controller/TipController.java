package com.inpocket.vitaverse.wellbeing.controller;
import com.inpocket.vitaverse.wellbeing.entity.Tip;
import com.inpocket.vitaverse.wellbeing.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tips")
public class TipController {
    @Autowired
    private TipService tipService;

    @GetMapping
    public List<Tip> getAllTips() {
        return tipService.getAllTips();
    }

    @GetMapping("/{id}")
    public Tip getTipById(@PathVariable long id) {
        Optional<Tip> tip = tipService.getTipById(id);
        return tip.orElse(null);
    }

    @PostMapping
    public Tip saveTip(@RequestBody Tip tip) {
        return tipService.saveTip(tip);
    }

    @DeleteMapping("/{id}")
    public void deleteTipById(@PathVariable long id) {
        tipService.deleteTipById(id);
    }
}
