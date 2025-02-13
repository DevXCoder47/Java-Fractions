package com.nikijv.javafractions.controller;

import com.nikijv.javafractions.model.Fraction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FractionController {
    List<Fraction> fractions = new ArrayList<Fraction>(List.of(
            new Fraction(1, 1,3),
            new Fraction(2, 3,3),
            new Fraction(3, 5,10),
            new Fraction(4, 7,49),
            new Fraction(5,30,120),
            new Fraction(6,10,19)
    ));

    @GetMapping("/fractions")
    public ResponseEntity<List<Fraction>> getFractions() {
        return ResponseEntity.ok(fractions);
    }

    @GetMapping("/fraction/proper/{id}")
    public ResponseEntity<?> isProper(@PathVariable int id) {
        Optional<Fraction> fractionOpt = fractions.stream()
                .filter(f -> f.getId() == id)
                .findFirst();
        if (fractionOpt.isPresent()) {
            Fraction fraction = fractionOpt.get();
            boolean isProper = fraction.getNumerator() < fraction.getDenominator();
            return ResponseEntity.ok(isProper);
        }
        return ResponseEntity.status(404).body("Fraction with id " + id + " not found.");
    }

    @GetMapping("/fraction/{id}/simplify")
    public ResponseEntity<Fraction> simplifyFraction(@PathVariable int id) {
        Optional<Fraction> fractionOpt = fractions.stream()
                .filter(f -> f.getId() == id)
                .findFirst();
        if (fractionOpt.isPresent()) {
            Fraction fraction = fractionOpt.get();
            reduce(fraction);
            return ResponseEntity.ok(fraction);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("fraction/{firstId}/plus/{secondId}")
    public ResponseEntity<Fraction> fractionSum(@PathVariable int firstId, @PathVariable int secondId) {
        Optional<Fraction> fractionOptFirst = fractions.stream()
                .filter(f -> f.getId() == firstId)
                .findFirst();
        Optional<Fraction> fractionOptSecond = fractions.stream()
                .filter(f -> f.getId() == secondId)
                .findFirst();
        if(fractionOptFirst.isPresent() && fractionOptSecond.isPresent()) {
            Fraction fractionFirst = fractionOptFirst.get();
            Fraction fractionSecond = fractionOptSecond.get();
            return ResponseEntity.ok(sum(fractionFirst, fractionSecond));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("fraction/{firstId}/minus/{secondId}")
    public ResponseEntity<Fraction> fractionDif(@PathVariable int firstId, @PathVariable int secondId) {
        Optional<Fraction> fractionOptFirst = fractions.stream()
                .filter(f -> f.getId() == firstId)
                .findFirst();
        Optional<Fraction> fractionOptSecond = fractions.stream()
                .filter(f -> f.getId() == secondId)
                .findFirst();
        if(fractionOptFirst.isPresent() && fractionOptSecond.isPresent()) {
            Fraction fractionFirst = fractionOptFirst.get();
            Fraction fractionSecond = fractionOptSecond.get();
            return ResponseEntity.ok(dif(fractionFirst, fractionSecond));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("fraction/{firstId}/multiply/{secondId}")
    public ResponseEntity<Fraction> fractionMult(@PathVariable int firstId, @PathVariable int secondId) {
        Optional<Fraction> fractionOptFirst = fractions.stream()
                .filter(f -> f.getId() == firstId)
                .findFirst();
        Optional<Fraction> fractionOptSecond = fractions.stream()
                .filter(f -> f.getId() == secondId)
                .findFirst();
        if(fractionOptFirst.isPresent() && fractionOptSecond.isPresent()) {
            Fraction fractionFirst = fractionOptFirst.get();
            Fraction fractionSecond = fractionOptSecond.get();
            return ResponseEntity.ok(mult(fractionFirst, fractionSecond));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("fraction/{firstId}/division/{secondId}")
    public ResponseEntity<Fraction> fractionDiv(@PathVariable int firstId, @PathVariable int secondId) {
        Optional<Fraction> fractionOptFirst = fractions.stream()
                .filter(f -> f.getId() == firstId)
                .findFirst();
        Optional<Fraction> fractionOptSecond = fractions.stream()
                .filter(f -> f.getId() == secondId)
                .findFirst();
        if(fractionOptFirst.isPresent() && fractionOptSecond.isPresent()) {
            Fraction fractionFirst = fractionOptFirst.get();
            Fraction fractionSecond = fractionOptSecond.get();
            return ResponseEntity.ok(div(fractionFirst, fractionSecond));
        }
        return ResponseEntity.notFound().build();
    }

    private void reduce(Fraction fraction) {
        int gcd = gcd(fraction.numerator, fraction.denominator);
        fraction.numerator /= gcd;
        fraction.denominator /= gcd;

        if (fraction.denominator < 0) {
            fraction.numerator *= -1;
            fraction.denominator *= -1;
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    private Fraction sum(Fraction f1, Fraction f2) {
        if(f1.denominator != f2.denominator) {
            int gcd = f1.denominator * f2.denominator;
            return new Fraction(0, f1.numerator * f2.denominator + f2.numerator * f1.denominator, gcd);
        }
        return new Fraction(0, f1.numerator + f2.numerator, f1.denominator);
    }

    private Fraction dif(Fraction f1, Fraction f2) {
        if(f1.denominator != f2.denominator) {
            int gcd = f1.denominator * f2.denominator;
            return new Fraction(0, f1.numerator * f2.denominator - f2.numerator * f1.denominator, gcd);
        }
        return new Fraction(0, f1.numerator - f2.numerator, f1.denominator);
    }

    private Fraction mult(Fraction f1, Fraction f2) {
        return new Fraction(0, f1.numerator * f2.numerator, f1.denominator * f2.denominator);
    }

    private Fraction div(Fraction f1, Fraction f2) {
        int temp = f2.denominator;
        f2.denominator = f2.numerator;
        f2.numerator = temp;
        return new Fraction(0, f1.numerator * f2.numerator, f1.denominator * f2.denominator);
    }
}
//http://localhost:8080/swagger-ui/index.html
