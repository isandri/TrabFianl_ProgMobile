package com.example.trabalhofinal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.dao.EncomendaDao;
import com.example.trabalhofinal.dao.UserDao;
import com.example.trabalhofinal.database.AppDatabase;
import com.example.trabalhofinal.entities.Encomenda;
import com.example.trabalhofinal.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Executors;

public class FragmentLogin extends Fragment {
    private static final String SALT = "$2a$10$1234567890123456789012";
    private static final String ARG_PARAM1 = "user-id";

    private UserDao userDao;
    private FragmentManager fragmentManager;

    public FragmentLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = requireActivity().getSupportFragmentManager();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = AppDatabase.getInstance(requireContext());
        userDao = db.userDao();

        TextInputEditText usrInput = view.findViewById(R.id.usuarioinput);
        TextInputEditText senhaInput = view.findViewById(R.id.senhainput);

        Button entrarButton = view.findViewById(R.id.btnentrar);
        Button cadastroButton = view.findViewById(R.id.btncadastro);

        entrarButton.setOnClickListener(v -> doLogin(usrInput.getText().toString(), senhaInput.getText().toString()));

        cadastroButton.setOnClickListener(v -> {
            String email = usrInput.getText().toString();
            String password = senhaInput.getText().toString();

            if (!email.trim().isEmpty() && !password.trim().isEmpty()) {
                createUser(email, password);
            } else {
                Toast.makeText(requireContext(), "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createUser(String email, String password) {
        User user = new User();

        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, SALT));

        Executors.newSingleThreadExecutor().execute(() -> {
            if (userDao.getUserByEmail(email) != null) {
                requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "E-mail já utilizado", Toast.LENGTH_SHORT).show());
            } else {
                long insertedId = userDao.insertUser(user);

                requireActivity().runOnUiThread(() -> {
                    if (insertedId > 0) {
                        Toast.makeText(requireContext(), "Usuário inserido com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Erro ao inserir o usuário", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void doLogin(String email, String password) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = userDao.getUserByEmailAndPassword(email, BCrypt.hashpw(password, SALT));

            requireActivity().runOnUiThread(() -> {
                if (user != null) {
                    FragmentLogado fragmentLogado = new FragmentLogado();
                    Bundle args = new Bundle();

                    args.putLong(ARG_PARAM1, user.getId());
                    fragmentLogado.setArguments(args);

                    MainActivity.replaceFragment(fragmentLogado, fragmentManager.beginTransaction());
                } else {
                    Toast.makeText(requireContext(), "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}