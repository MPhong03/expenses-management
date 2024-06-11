import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../services/authAPIService.dart';

class SignInPresenter {
  final AuthApiService apiService;
  final BuildContext context;
  final FlutterSecureStorage storage = const FlutterSecureStorage();

  SignInPresenter(this.apiService, this.context);

  Future<void> signIn(String username, String password, bool isRemember) async {
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
      final response = await apiService.signIn(username, password, isRemember);

      if (response.statusCode == 200) {
        final token = response.body;
        await storage.write(key: 'jwt_token', value: token);
        Fluttertoast.showToast(msg: "User logged in successfully");
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.pushNamed(context, '/home');
      } else {
        Fluttertoast.showToast(msg: "Failed to sign in: ${response.body}");
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
