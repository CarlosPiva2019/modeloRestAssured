package constants;

public class Constants {

    //menssagens de erro
    public static final String ERROR_MESSAGE_INVALID_CREDENTIALS = "Invalid credentials";
    public static final String ERROR_MESSAGE_TO_LOWERCASE = "Cannot read properties of null (reading 'toLowerCase')";
    public static final String NONEXISTENT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhdHVueTAiLCJlbWFpbCI6ImF0dW55MEBzb2h1LmNvbSIsImZpcnN0TmFtZSI6IlRlcnJ5IiwibGFzdE5hbWUiOiJNZWRodXJzdCIsImdlbmRlciI6Im1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vcm9ib2hhc2gub3JnL1RlcnJ5LnBuZz9zZXQ9c2V0NCIsImlhdCI6MTcwODEzNjY5NSwiZXhwIjoxNzA4MTQwMjk1fQ.dE_7Oihj2v9L_MfIatQqdPdSR_1KFJY_QcZK-T54Jb";

    //endpoints
    public static String BASE_URI = "https://dummyjson.com";
    public static String ENDPOINT_USERS = "/users";
    public static String ENDPOINT_TOKEN_CREATION = "/auth/login";
    public static String ENDPOINT_PRODUCTS_AUTH = "/auth/products";
    public static String ENDPOINT_PRODUCTS_CREATION = "/products/add";
    public static String ENDPOINT_PRODUCTS_ALL = "/products";
    public static String ENDPOINT_PRODUCTS_BY_ID = "/products/{id}";
}
