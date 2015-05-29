package com.packt.webstore.controller;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.IproductRepository;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.IproductService;

@RequestMapping("/products")
@Controller
public class ProductController {
	
	@Autowired
	private IproductService productService;
	
	/*
	 *  This is the default request mapping method of ProductController class
	 */
	  @RequestMapping
	  public String list(Model model) {
		/*
	    Product iphone = new Product("P1234","iPhone 5s", new  BigDecimal(500));
	    iphone.setDescription("Apple iPhone 5s smartphone with 4.00-inch 640x1136 display and 8-megapixel rear camera");
	    iphone.setCategory("Smart Phone");
	    iphone.setManufacturer("Apple");
	    iphone.setUnitsInStock(1000);
	    */
	    model.addAttribute("products", productService.getAllProducts());
	    
	    return "products";
	  }
	  @RequestMapping("/all")
	  public String allProducts(Model model) {
	    model.addAttribute("products", productService.getAllProducts());
	    
	  return "products";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/laptop
       *	http://localhost:8080/webstore/products/tablet
       *    Result: 
       *          productCategory contains either laptop or tablet
	   */
	  @RequestMapping("/{category}")
	  public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		  List<Product> products =productService.getProductsByCategory(productCategory);
		  if(products == null || products.isEmpty()){
			  throw new NoProductsFoundUnderCategoryException();
		  }
		  model.addAttribute("products", products);
	    return "products";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/filter/ByCriteria;brand=google,dell;category=tablet,laptop
       *    Result: 
       *          filterParams contains {brand={google,dell},category={tablet,laptop}}
	   */
	  @RequestMapping("/filter/{ByCriteria}")
	  public String getProductsByFilter(@MatrixVariable(pathVar= "ByCriteria") Map<String,List<String>> filterParams
			  ,Model model) {
	    model.addAttribute("products", productService.getProductsByFilter(filterParams));
	    return "products";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/filter/ByPrice;low=200;high=400
       *    Result: 
       *          filterParams contains {low={200},high={400}}
	   */
	  @RequestMapping("/prices/{ByPrice}")
	  public String getProductsByPrice(@MatrixVariable(pathVar= "ByPrice") Map<String,List<String>> filterParams
			  ,Model model) {
	    model.addAttribute("products", productService.getProductsByPriceFilter(filterParams));
	    return "products";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/product?id=P1234 
       *    Result: 
       *          productId contains P1234
	   */
	  @RequestMapping("/product")
	  public String getProductById(@RequestParam("id") String productId, Model model) {
	    model.addAttribute("product", productService.getProductById(productId));
	    return "product";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/tablet/price;low=200;high=400?manufacturer="Google" 
       *    Result: 
       *          productId contains P1234
	   */
	  @RequestMapping("/{category}/{price}")
	  public String filterProducts(@PathVariable("category") String productCategory, 
			  @MatrixVariable(pathVar= "price") Map<String,List<String>> filterParams,
			  @RequestParam("manufacturer") String manufacturer,
			  Model model) {
		  List <Product> prodBycat=productService.getProductsByCategory(productCategory);
		  Set <Product> prodByCategory= new HashSet<Product>(prodBycat);
		  Set <Product> prodByPrice = productService.getProductsByPriceFilter(filterParams);
		  Set <Product> prodByManufacture= new HashSet<Product>(productService.getProductsByManufacturer(manufacturer));
		  // Find intersection items from all sets
		  prodByCategory.retainAll(prodByPrice);
		  prodByCategory.retainAll(prodByManufacture);
		  model.addAttribute("products", prodByCategory);
	    return "products";
	  }
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/add 
	   *    Request method: GET
       *    Result: 
       *          N/A
	   *
	  @RequestMapping(value = "/add", method = RequestMethod.GET)
	  public String getAddNewProductForm(Model model) {
	     Product newProduct = new Product();
	     /*
	      *  assign form-backing bean in Spring MVC
	      *  See line <form:form  modelAttribute="newProduct" class="form-horizontal">
	      *  in addProduct.jsp
	      *
	     model.addAttribute("newProduct", newProduct);
	     return "addProduct";
	  } */

	  /*    Alternative form of implementing function getAddNewProductForm()
	   *    By using the annotation @ModelAttribute at the function parameter,
	   *    Spring MVC would know that it shoudl create an object of 
	   *    Product and attach it to the model under the name newProduct.
	   */
	  @RequestMapping(value = "/add", method = RequestMethod.GET)
	  public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
	    return "addProduct";
	  }
	  
	  /*    URL samples
	   * 	http://localhost:8080/webstore/products/add 
	   *    Request method: POST
       *    Result: 
       *          N/A
	   */
	  @RequestMapping(value = "/add", method = RequestMethod.POST)
	  public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct,
			  BindingResult result,HttpServletRequest request) {
		  if(result.hasErrors()) {
			  return "addProduct";
		  }
		  
		  String[] suppressedFields = result.getSuppressedFields();
		  if (suppressedFields.length > 0) {
		    throw new RuntimeException("Attempting to bind disallowed fields: " 
		     + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		  }
		  MultipartFile productImage = newProduct.getProductImage();
		  String rootDirectory =request.getSession().getServletContext().getRealPath("/");

		  if (productImage!=null && !productImage.isEmpty()) {
		    try {
		      productImage.transferTo(new File(rootDirectory+"resources\\images\\"+newProduct.getProductId() + ".png"));
		    } catch (Exception e) {
		      throw new RuntimeException("Product Image saving failed",e);
		    }
		  }
		  MultipartFile productManual = newProduct.getProductManual();		  

		  if (productManual!=null && !productManual.isEmpty()) {
		    try {
		      productImage.transferTo(new File(rootDirectory+"resources\\pdf\\"+newProduct.getProductId() + ".pdf"));
		    } catch (Exception e) {
		      throw new RuntimeException("Product Manual saving failed",e);
		    }
		  }
	      productService.addProduct(newProduct);
	      return "redirect:/products";
	  }
	  @InitBinder
	  public void initialiseBinder(WebDataBinder binder) {
	     binder.setDisallowedFields("discontinued");
	     binder.setAllowedFields("productId","name","unitPrice","description",
	    		 "manufacturer","category","unitsInStock", "productImage",
	    		 "productManual","language");
	     
	  }
	  @ExceptionHandler(ProductNotFoundException.class)
	  public ModelAndView handleError(HttpServletRequest req,ProductNotFoundException exception) {
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("invalidProductId", exception.getProductId());
	    mav.addObject("exception", exception);
	    mav.addObject("url",req.getRequestURL()+"?"+req.getQueryString());
	    mav.setViewName("productNotFound");
	    return mav;
	  }
	  @RequestMapping("/invalidPromoCode")
	  public String invalidPromoCode() {
	    return "invalidPromoCode";
	  }
	  
}
