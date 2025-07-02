package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CurvePointService extends AbstractCrudService<CurvePoint> {

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        super(curvePointRepository);
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public Integer create(CurvePoint curvePoint) {
        Assert.isNull(curvePoint.getId(), "ID needs to be null");
        validateFields(curvePoint);

        CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
        return savedCurvePoint.getId();
    }

    @Override
    public void update(CurvePoint curvePoint, Integer id) {
        CurvePoint curvePointToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(curvePoint);

        curvePointToUpdate.update(curvePoint);

        curvePointRepository.save(curvePointToUpdate);
    }

    private void validateFields(CurvePoint curvePoint) {
        Assert.notNull(curvePoint, "Object must not be null");
        Assert.isTrue(curvePoint.getCurveId() > 0, "CurveId must be positive");
        Assert.isTrue(curvePoint.getTerm() > 0, "Term must be positive");
        Assert.isTrue(curvePoint.getValue() > 0, "Value must be positive");
    }
}
