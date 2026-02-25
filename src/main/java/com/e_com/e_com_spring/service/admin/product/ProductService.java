package com.e_com.e_com_spring.service.admin.product;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.service.admin.product.logic.IProductLogic;
import com.e_com.e_com_spring.service.admin.user.checker.IChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final IProductLogic productLogic;
    private final IChecker userChecker;

    @Override
    public ProductGetDto create(ProductPostDto postDto, User currentUser) {
        userChecker.canPerformAdminAction(currentUser); // TODO: change to currentUser.isAdmin()
        return productLogic.create(postDto);
    }

    @Override
    public List<ProductGetDto> search(Pageable page) {
        return List.of();
    }

    @Override
    public ProductGetDto getById(Long id) {
        return productLogic.getById(id);
    }

    @Override
    public ProductGetDto update(Long id, ProductPostDto putDto, User currentUser) {
        userChecker.canPerformAdminAction(currentUser);
        return productLogic.update(id, putDto);
    }

    @Override
    public void delete(Long id, User currentUser) {
        userChecker.canPerformAdminAction(currentUser);
        productLogic.delete(id);
    }
}
