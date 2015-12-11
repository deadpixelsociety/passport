# passport
An Android text validation library for Kotlin.

    @Suppress("UNCHECKED_CAST")
    fun <T : View> Activity.findView(id: Int): T = findViewById(id) as T
    
    class MainActivity() : AppCompatActivity() {
      val editText by lazy { findView<EditText>(android.R.id.edit) }
      val textInputLayout by lazy { findView<TextInputLayout>(android.R.id.inputArea) }
      val button by lazy { findView<Button>(android.R.id.button1) }
      val text by lazy { findView<TextView>(android.R.id.text1) }
    
      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      
        setContentView(R.layout.activity_main)
      
        // Add pre-defined validation rules via the validator wrapper(s)
        // Can also add raw functions w/ message via add overload.
        editText.validator().add(Passport.Rules.phoneRequired)
        textInputLayout.validator().add(Passport.Rules.emailRequired)
        
        button.setOnClickListener {
            clearErrors() // Clear all current validator errors.
      
            // Search the entire view tree for validators and validate their views.
            // Can also be called on a ViewGroup to narrow the search.
            // Can also validate individual validators.
            if (validateRules())
                text.text = "Validation passed!"
            else
                text.text = "Validation failed!"
        }
      }

      override fun onPause() {
          clearRules() // Remove all current validators and rules via entire view tree
    
          super.onPause()
      }
    }
