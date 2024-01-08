package com.codegym.controller;

import com.codegym.model.Cart;
import com.codegym.model.Product;
import com.codegym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {

    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("/shop")
    public ModelAndView showShop() {
        ModelAndView modelAndView = new ModelAndView("/shop");
        Iterable<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long id,
                            @ModelAttribute("cart") Cart cart,
                            @RequestParam("action") String action) {
        Optional<Product> product = productService.findById(id);
        System.out.println("id:" + id);
        if (!product.isPresent()) {
            return "/error-404";
        }

        if (action.equals("show")) {
            cart.addProduct(product.get());
            return "redirect:/shopping-cart";
        }
        cart.addProduct(product.get());
        return "redirect:/shop";
    }

    @GetMapping("/shopping-cart")
    public ModelAndView showCart(@ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView("/cart");
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id,
                         Cart cart) {
        Optional<Product> product = productService.findById(id);
        cart.removeCartItem(product.get());
        return "redirect:/shopping-cart";
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/view");
            modelAndView.addObject("product", product.orElse(null));
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/update");
            modelAndView.addObject("product", product.orElse(null));
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam("quantity") Integer quantity, Product product, Cart cart, Model model) {
        cart.updateCartItem(product, quantity);
        return "redirect:/shopping-cart";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Cart cart) {
        cart.deleteCartItem(productService.findById(id).get());
        return "redirect:/shopping-cart";
    }
}
