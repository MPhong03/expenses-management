import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../services/auth_api_service.dart';

class AuthPresenter {
  final AuthApiService apiService;
  final BuildContext context;
  final FlutterSecureStorage storage = const FlutterSecureStorage();

  AuthPresenter(this.apiService, this.context);

  // SIGN IN
  Future<void> signIn(String username, String password, bool isRemember, BuildContext context) async {
    // Show loading dialog
    _showLoadingDialog();

    try {
      final response = await apiService.signIn(username, password, isRemember);

      if (response.statusCode == 200) {
        final token = response.body;
        await storage.write(key: 'jwt_token', value: token);
        Fluttertoast.showToast(msg: "User logged in successfully");
        if (context.mounted) {
          Navigator.of(context, rootNavigator: true).pop();
          Navigator.pushNamed(context, '/home');
        }
      } else {
        if (context.mounted) {
          Fluttertoast.showToast(msg: "Failed to sign in");
          Navigator.of(context).pop();
        }
      }
    } catch (e) {
      if (context.mounted) {
        Fluttertoast.showToast(msg: "Error: $e");
        Navigator.of(context).pop();
      }
    }
  }

  // SIGN UP
  Future<void> signUp(String username, String email, String password, String confirmPassword, BuildContext context) async {
    if (password != confirmPassword) {
      Fluttertoast.showToast(msg: "Passwords do not match");
      return;
    }

    if (password.length < 6 || !RegExp(r'^(?=.*[A-Z])(?=.*\d).+$').hasMatch(password)) {
      Fluttertoast.showToast(msg: "Password must be at least 6 characters long, include at least one uppercase letter and one number.");
      return;
    }

    _showLoadingDialog();

    try {
      final response = await apiService.signUp(username, email, password);

      if (response.statusCode == 200) {
        Fluttertoast.showToast(msg: "User registered successfully");
        if (context.mounted) {
          Navigator.of(context, rootNavigator: true).pop();
          Navigator.pushNamed(context, '/login');
        }
      } else {
        Fluttertoast.showToast(msg: "Failed to register: ${response.body}");
      }
    } catch (e) {
      Fluttertoast.showToast(msg: "Error: $e");
    } finally {
      if (context.mounted) {
        if (Navigator.canPop(context)) {
          Navigator.of(context).pop();
        }
      }
    }
  }

  // SEND OTP
  Future<void> sendOTP(BuildContext context, String email) async {
    _showLoadingDialog();

    try {
      final response = await apiService.sendOTP(email);

      if (response.statusCode == 200) {
        Fluttertoast.showToast(msg: "OTP sent to your email");
        if (context.mounted) {
          Navigator.of(context, rootNavigator: true).pop();
          Navigator.pushNamed(context, '/verify-otp', arguments: email);
        }

      } else {
        if (context.mounted) {
          Navigator.of(context).pop(); // Hide loading dialog
          Fluttertoast.showToast(msg: "Failed to send OTP: ${response.body}");
        }
      }
    } catch (e) {
      if (context.mounted) {
        Navigator.of(context).pop(); // Hide loading dialog
        Fluttertoast.showToast(msg: "Error: $e");
      }
    }
  }

  // VERIFY OTP
  Future<void> verifyOTP(BuildContext context, String email, String otp) async {
    _showLoadingDialog();

    try {
      final response = await apiService.verifyOTP(email, otp);

      if (response.statusCode == 200) {
        Fluttertoast.showToast(msg: "OTP verified successfully");
        if (context.mounted) {
          Navigator.of(context, rootNavigator: true).pop();
          Navigator.pushNamed(context, '/reset-password', arguments: email);
        }

      } else {
        if (context.mounted) {
          Navigator.of(context).pop(); // Hide loading dialog
          Fluttertoast.showToast(msg: "Failed to verify OTP: ${response.body}");
        }
      }
    } catch (e) {
      if (context.mounted) {
        Navigator.of(context).pop(); // Hide loading dialog
        Fluttertoast.showToast(msg: "Error: $e");
      }

    }
  }

  // RESET PASSWORD
  Future<void> resetPassword(BuildContext context, String email, String newPassword, String confirmPassword) async {
    if (newPassword != confirmPassword) {
      Fluttertoast.showToast(msg: "Passwords do not match");
      return;
    }

    if (newPassword.length < 6 || !RegExp(r'^(?=.*[A-Z])(?=.*\d).+$').hasMatch(newPassword)) {
      Fluttertoast.showToast(msg: "Password must be at least 6 characters long, include at least one uppercase letter and one number.");
      return;
    }

    _showLoadingDialog();

    try {
      final response = await apiService.resetPassword(email, newPassword);

      if (response.statusCode == 200) {
        Fluttertoast.showToast(msg: "Password changed successfully");
        if (context.mounted) {
          Navigator.of(context, rootNavigator: true).pop();
          Navigator.pushNamed(context, '/login');
        }

      } else {
        if (context.mounted) {
          Navigator.of(context).pop(); // Hide loading dialog
          Fluttertoast.showToast(msg: "Failed to change password: ${response.body}");
        }
      }
    } catch (e) {
      if (context.mounted) {
        Navigator.of(context).pop(); // Hide loading dialog
        Fluttertoast.showToast(msg: "Error: $e");
      }
    }
  }

  // CHECK LOGIN STATUS
  Future<bool> isLogin() async {
    String? token = await storage.read(key: 'jwt_token');
    return token != null && token.isNotEmpty;
  }

  // LOGOUT
  Future<void> logout() async {
    await storage.delete(key: 'jwt_token');
    Fluttertoast.showToast(msg: "User logged out successfully");
    if (context.mounted) {
      Navigator.pushNamedAndRemoveUntil(context, '/login', (route) => false);
    }
  }

  // LOADING CIRCLE DIALOG
  void _showLoadingDialog() {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }
}
