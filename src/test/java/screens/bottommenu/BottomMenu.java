package screens.bottommenu;

public enum BottomMenu {
    SETTINGS("tabSettings"),
    HOLDS("tabHolds"),
    BOOKS("tabBooks"),
    CATALOG("tabCatalog");

    private String catalog;

    BottomMenu(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return catalog;
    }
}
