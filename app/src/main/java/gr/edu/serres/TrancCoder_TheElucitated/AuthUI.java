package gr.edu.serres.TrancCoder_TheElucitated;

/**
 * Created by Alex on 29/11/2016.
 */

/*

public class AuthUI {
    public static AuthUI getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }


    public static AuthUI getInstance(FirebaseApp app) {
        AuthUI authUi;
        synchronized (INSTANCES) {
            authUi = INSTANCES.get(app);
            if (authUi == null) {
                authUi = new AuthUI(app);
                INSTANCES.put(app, authUi);
            }
        }
        return authUi;
    }

    public static final String EMAIL_PROVIDER = EmailAuthProvider.PROVIDER_ID;


    public static final String GOOGLE_PROVIDER = GoogleAuthProvider.PROVIDER_ID;


    public static final String FACEBOOK_PROVIDER = FacebookAuthProvider.PROVIDER_ID;


    public static final int NO_LOGO = -1;


    public static final Set<String> SUPPORTED_PROVIDERS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    EMAIL_PROVIDER,
                    GOOGLE_PROVIDER,
                    FACEBOOK_PROVIDER

                    )));

    private static final IdentityHashMap<FirebaseApp, AuthUI> INSTANCES = new IdentityHashMap<>();

    private final FirebaseApp mApp;
    private final FirebaseAuth mAuth;

    private AuthUI(FirebaseApp app) {
        mApp = app;
        mAuth = FirebaseAuth.getInstance(mApp);
    }

}
*/
