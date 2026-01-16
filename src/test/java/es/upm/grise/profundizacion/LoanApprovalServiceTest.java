package es.upm.grise.profundizacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.LoanApprovalService.Applicant;
import es.upm.grise.profundizacion.LoanApprovalService.Decision;

public class LoanApprovalServiceTest {

    private final LoanApprovalService service = new LoanApprovalService();

    @Test
    void test_camino_basico_4() {
        assertEquals(Decision.APPROVED, service.evaluateLoan(new Applicant(2500, 600, false, true), 10, 10));
    }

    
    @Test
    void test_camino_basico_6() {
        assertEquals(Decision.APPROVED, service.evaluateLoan(new Applicant(10, 650, false, true), 100, 10));
    }

    
    @Test
    void test_camino_basico_7() {
        assertEquals(Decision.REJECTED, service.evaluateLoan(new Applicant(10, 100, false, true), 100, 10));
    }
    
    @Test
    void test_camino_basico_8() {
        assertEquals(Decision.REJECTED, service.evaluateLoan(new Applicant(10, 500, false, true), 100, 10));
    }
    
    @Test
    void test_camino_basico_9() {
        assertEquals(Decision.REJECTED, service.evaluateLoan(new Applicant(2500, 500, true, true), 100, 10));
    }
    
    @Test
    void test_camino_basico_11() {
        assertEquals(Decision.APPROVED, service.evaluateLoan(new Applicant(2500, 650, false, true), 100, 10));
    }

    
    
    @Test
    void test_camino_basico_16() {
        assertEquals(Decision.MANUAL_REVIEW, service.evaluateLoan(new Applicant(2500, 500, false, false), 100, 10));
    }
    
    @Test
    void test_camino_basico_18() {
        assertEquals(Decision.MANUAL_REVIEW, service.evaluateLoan(new Applicant(10, 650, false, false), 100, 10));
    }

    
    
    @Test
    void test_camino_basico_22() {
        assertEquals(Decision.MANUAL_REVIEW, service.evaluateLoan(new Applicant(2500, 500, false, true), 100, 10));
    }

    
    
    @Test
    void test_camino_basico_30() {
        assertEquals(Decision.MANUAL_REVIEW, service.evaluateLoan(new Applicant(10, 650, true, true), 100, 10));
    }

    @Test
    void test_camino_basico_31() {
        assertThrows(NullPointerException.class, () -> service.evaluateLoan(null, 0, 0));
    }

}
