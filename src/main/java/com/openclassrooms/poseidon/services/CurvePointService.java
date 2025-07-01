package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

@Service
public class CurvePointService extends AbstractCrudService<CurvePoint> {

    public CurvePointService(CurvePointRepository curvePointRepository) {
        super(curvePointRepository);
    }

}
