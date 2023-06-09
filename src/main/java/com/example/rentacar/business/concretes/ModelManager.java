package com.example.rentacar.business.concretes;

import com.example.rentacar.business.abstracts.ModelService;
import com.example.rentacar.business.dto.requests.create.CreateModelRequest;
import com.example.rentacar.business.dto.requests.update.UpdateModelRequest;
import com.example.rentacar.business.dto.responses.create.CreateModelResponse;
import com.example.rentacar.business.dto.responses.get.GetAllBrandsResponse;
import com.example.rentacar.business.dto.responses.get.GetAllModelsResponse;
import com.example.rentacar.business.dto.responses.get.GetModelResponse;
import com.example.rentacar.business.dto.responses.update.UpdateModelResponse;
import com.example.rentacar.entities.Model;
import com.example.rentacar.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private final ModelRepository repository;
    private final ModelMapper mapper;




    @Override
    public List<GetAllModelsResponse> getAll() {
        List<Model> models = repository.findAll();
        List<GetAllModelsResponse> response = models
                .stream()
                .map(model-> mapper.map(model , GetAllModelsResponse.class))
                .toList();
        return response;
    }


    @Override
    public GetModelResponse getById(int id) {
        checkIfModelExistsById(id);
        Model model=repository.findById(id).orElseThrow();
        GetModelResponse response = mapper.map(model,GetModelResponse.class);
        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        checkIfModelExistsByName(request.getName());
        Model model = mapper.map(request, Model.class);
        repository.save(model);
        CreateModelResponse response = mapper.map(model,CreateModelResponse.class);

        return response;
    }

    @Override
    public UpdateModelResponse update(int id, UpdateModelRequest request) {
        checkIfModelExistsById(id);
        Model model = mapper.map(request, Model.class);
        model.setId(id);
        repository.save(model);
        UpdateModelResponse response = mapper.map(model,UpdateModelResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfModelExistsById(id);
        repository.deleteById(id);


    }
    private void checkIfModelExistsById(int id){
        if(!repository.existsById(id)){
            throw new RuntimeException("böyle bir değer mevcut değil");

        }
    }
    private void checkIfModelExistsByName(String name){
        if(repository.existsByNameIgnoreCase(name)){
            throw new RuntimeException("böyle bir değer sistemde kayıtlı");
        }
    }
}
