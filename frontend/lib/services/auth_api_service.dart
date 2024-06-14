import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class AuthApiService {
  final String _baseUrl = dotenv.env['API_URL'] ?? 'http://localhost:8080';

  Future<http.Response> signUp(String username, String email, String password) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/signUp'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'email': email,
        'password': password,
      }),
    );
  }

  Future<http.Response> signIn(String username, String password, bool isRemember) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/signIn?isRemember=$isRemember'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'password': password,
      }),
    );
  }

  Future<http.Response> sendOtp(String email) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/getOTP'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'email': email,
      }),
    );
  }

  Future<http.Response> sendOTP(String email) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/getOTP'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{'email': email}),
    );
  }

  Future<http.Response> verifyOTP(String email, String otp) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/checkOTP'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{'email': email, 'otp': otp}),
    );
  }

  Future<http.Response> resetPassword(String email, String newPassword) {
    return http.post(
      Uri.parse('$_baseUrl/api/auth/resetPassword'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{'email': email, 'newPassword': newPassword}),
    );
  }
}
