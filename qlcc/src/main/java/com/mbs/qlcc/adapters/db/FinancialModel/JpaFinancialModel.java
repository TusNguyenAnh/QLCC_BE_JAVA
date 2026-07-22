package com.mbs.qlcc.adapters.db.FinancialModel;

import com.mbs.qlcc.entities.FinancialModel.FinancialModel;
import com.mbs.qlcc.usecases.output.FinancialModel.IFinancialModelDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFinancialModel implements IFinancialModelDsGateway {
    private final JpaFinancialModelRepository repository;

    @Override
    public List<FinancialModel> getFinancialModels() {
        return repository.findAll().stream().map(this::mapToFinancialModel).toList();
    }

    @Override
    public FinancialModel getFinancialModelById(String id) {
        return repository.findById(id).map(this::mapToFinancialModel).orElse(null);
    }

    @Override
    public FinancialModel getFinancialModelByName(String name) {
        return repository.findByType(name).map(this::mapToFinancialModel).orElse(null);
    }

    private FinancialModel mapToFinancialModel(FinancialModelDataMapper dataMapper) {
        return new FinancialModel(
                dataMapper.getId(),
                dataMapper.getName(),
                dataMapper.getType(),
                dataMapper.isDeleted(),
                dataMapper.getCreatedAt(),
                dataMapper.getUpdatedAt(),
                dataMapper.getDeletedAt()
        );
    }
}
