package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.Tip;
import java.util.List;
import java.util.Optional;

public interface TipService {
    List<Tip> getAllTips();
    Optional<Tip> getTipById(long id);
    Tip saveTip(Tip tip);
    void deleteTipById(long id);
}



