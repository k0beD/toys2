package be.vdab.toys2.services;

import be.vdab.toys2.domain.Product;
import be.vdab.toys2.exceptions.ProductNietGevondenException;
import be.vdab.toys2.repositories.OrderRepository;
import be.vdab.toys2.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void ship(long id, int aantal) {
        productRepository.findAndLockById(id).orElseThrow(() ->new ProductNietGevondenException(id)).ship(aantal);
    }
}
