package es.upm.grise.profundizacion;

import java.util.Objects;

public class LoanApprovalService {

    /**
     * Método X: contiene decisiones encadenadas y compuestas para análisis estructural.
     *
     * Regla (simplificada):
     * - Entradas inválidas -> excepción
     * - Score < 500 -> REJECTED
     * - 500..649 -> si income alto y no tiene impagos -> MANUAL_REVIEW; si no -> REJECTED
     * - >= 650 -> si amount <= income*8 -> APPROVED; si no -> MANUAL_REVIEW
     * - Además, si el cliente es VIP y score>=600 y no tiene impagos -> se eleva a APPROVED si estaba en MANUAL_REVIEW
     */
    public Decision evaluateLoan(
            Applicant applicant,
            int amountRequested,
            int termMonths
    ) { // 1
        validate(applicant, amountRequested, termMonths); // 2

        int score = applicant.creditScore();
        boolean hasDefaults = applicant.hasRecentDefaults();
        int income = applicant.monthlyIncome();

        Decision decision;

        if (score < 500) { // 3.1
            decision = Decision.REJECTED; //3.2
        } else if (score < 650) { // 4
            if (income >= 2500 /* 5.1 */ && !hasDefaults /* 5.2 */) {
                decision = Decision.MANUAL_REVIEW; // 6
            } else {
                decision = Decision.REJECTED; // 7
            }
        } else {
            if (amountRequested <= income * 8) { // 8
                decision = Decision.APPROVED; // 9
            } else {
                decision = Decision.MANUAL_REVIEW; // 10
            }
        }

        if (decision == Decision.MANUAL_REVIEW /* 11.1 */
                && applicant.isVip() /* 11.2 */
                && score >= 600 /* 11.3 */
                && !hasDefaults  /* 11.4 */) {
            decision = Decision.APPROVED; // 12
        }

        return decision; // 13
    }

    private void validate(Applicant applicant, int amountRequested, int termMonths) {
        Objects.requireNonNull(applicant, "applicant cannot be null");
        if (amountRequested <= 0) {
            throw new IllegalArgumentException("amountRequested must be > 0");
        }
        if (termMonths < 6 || termMonths > 84) {
            throw new IllegalArgumentException("termMonths must be between 6 and 84");
        }
        if (applicant.monthlyIncome() <= 0) {
            throw new IllegalArgumentException("monthlyIncome must be > 0");
        }
        if (applicant.creditScore() < 0 || applicant.creditScore() > 850) {
            throw new IllegalArgumentException("creditScore must be between 0 and 850");
        }
    }

    public enum Decision {
        APPROVED, MANUAL_REVIEW, REJECTED
    }

    public record Applicant(int monthlyIncome, int creditScore, boolean hasRecentDefaults, boolean isVip) { }
}

