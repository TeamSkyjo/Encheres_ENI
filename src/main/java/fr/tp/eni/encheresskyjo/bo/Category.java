package fr.tp.eni.encheresskyjo.bo;

public class Category {

    private int categoryId;
    private String label;

    public Category() {
    }

    public Category(String label) {
        this.label = label;
    }

    public Category(int categoryId, String label) {
        this.categoryId = categoryId;
        this.label = label;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category categorie = (Category) o;
        return categoryId == categorie.categoryId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("categoryId=").append(categoryId);
        sb.append(", label='").append(label).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
