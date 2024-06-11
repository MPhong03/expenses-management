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
}
