import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../services/authAPIService.dart';

class SignUpPresenter {
  final AuthApiService apiService;
  final BuildContext context;

  SignUpPresenter(this.apiService, this.context);

  Future<void> signUp(String username, String email, String password, String confirmPassword) async {
    if (password != confirmPassword) {
      Fluttertoast.showToast(msg: "Passwords do not match");
      return;
    }

    if (password.length < 6 || !RegExp(r'^(?=.*[A-Z])(?=.*\d).+$').hasMatch(password)) {
      Fluttertoast.showToast(msg: "Password must be at least 6 characters long, include at least one uppercase letter and one number.");
      return;
    }

    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      },
    );

    try {
      final response = await apiService.signUp(username, email, password);

      if (response.statusCode == 200) {
        Fluttertoast.showToast(msg: "User registered successfully");
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.pushNamed(context, '/login');
      } else {
        Fluttertoast.showToast(msg: "Failed to register: ${response.body}");
      }
    } catch (e) {
      Fluttertoast.showToast(msg: "Error: $e");
    } finally {
      if (Navigator.canPop(context)) {
        Navigator.of(context).pop();
      }
    }
  }
}
