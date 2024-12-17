package com.inpocket.vitaverse.wellbeing.service;
import com.inpocket.vitaverse.wellbeing.entity.Tip;
import com.inpocket.vitaverse.wellbeing.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TipServiceImpl implements TipService {
    @Autowired
    private TipRepository tipRepository;

    @Override
    public List<Tip> getAllTips() {
        return tipRepository.findAll();
    }

    @Override
    public Optional<Tip> getTipById(long id) {
        return tipRepository.findById(id);
    }

    @Override
    public Tip saveTip(Tip tip) {
        return tipRepository.save(tip);
    }

    @Override
    public void deleteTipById(long id) {
        tipRepository.deleteById(id);
    }
}
