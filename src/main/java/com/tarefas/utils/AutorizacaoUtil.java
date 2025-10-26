package com.tarefas.utils;

import java.util.Arrays;

import com.tarefas.models.enums.Perfil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AutorizacaoUtil {
	
	public static boolean temPerfil(HttpServletRequest request, Perfil perfis) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        Perfil perfil = (Perfil) session.getAttribute("usuarioPerfil");
        return Arrays.asList(perfis).contains(perfil);
    }
}
