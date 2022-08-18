package dev.om.library.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    public List<Checkout> getCheckoutsByUserID(UUID userID) {
        return checkoutRepository.findAllByUserID(userID);
    }

}
