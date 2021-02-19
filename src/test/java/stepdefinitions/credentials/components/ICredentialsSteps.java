package stepdefinitions.credentials.components;

public interface ICredentialsSteps {
    void checkLoginIsPerformedSuccessfully();

    void textOnLogoutButtonIsChangedToLogInOnAccountScreen();

    void clickLogOut();

    void enterCredentialsForLibraryAccount(String libraryName);
}
