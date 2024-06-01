package mackerel.dontworry.budgetguide.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FixedExDTO {

    private String username;
    private boolean food;
    private boolean entertainment;
    private boolean transportation;
    private boolean housing;
    private boolean education;
    private boolean health;
    private boolean communication;
    private boolean necessity;
    private boolean saving;
    private boolean fashion;
    private boolean present;
    private boolean travel;
    private boolean parent;
    private boolean other;

    public String getFixedEXsAsString() {
        StringBuilder categoriesBuilder = new StringBuilder();
        if (food) {
            categoriesBuilder.append("food").append(",");
        }
        if (entertainment) {
            categoriesBuilder.append("entertainment").append(",");
        }
        if (transportation) {
            categoriesBuilder.append("transportation").append(",");
        }
        if (housing) {
            categoriesBuilder.append("housing").append(",");
        }
        if (education) {
            categoriesBuilder.append("education").append(",");
        }
        if (health) {
            categoriesBuilder.append("health").append(",");
        }
        if (communication) {
            categoriesBuilder.append("communication").append(",");
        }
        if (necessity) {
            categoriesBuilder.append("necessity").append(",");
        }
        if (saving) {
            categoriesBuilder.append("saving").append(",");
        }
        if (fashion) {
            categoriesBuilder.append("fashion").append(",");
        }
        if (present) {
            categoriesBuilder.append("present").append(",");
        }
        if (travel) {
            categoriesBuilder.append("travel").append(",");
        }
        if (parent) {
            categoriesBuilder.append("parent").append(",");
        }
        if (other) {
            categoriesBuilder.append("other").append(",");
        }

        // 마지막 구분자 제거
        if (categoriesBuilder.length() > 0) {
            categoriesBuilder.deleteCharAt(categoriesBuilder.length() - 1);
        }

        return categoriesBuilder.toString();
    }
}
