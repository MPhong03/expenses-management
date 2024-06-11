import 'dart:convert';

class UserDTO {
  int? userID;
  String? username;
  String? email;
  String? firstName;
  String? lastName;
  String? password;
  DateTime? createdAt;
  DateTime? birthDate;
  String? gender;
  String? otp;
  DateTime? otpExpiryTime;
  bool? canChangePassword;

  UserDTO({
    this.userID,
    this.username,
    this.email,
    this.firstName,
    this.lastName,
    this.password,
    this.createdAt,
    this.birthDate,
    this.gender,
    this.otp,
    this.otpExpiryTime,
    this.canChangePassword,
  });

  factory UserDTO.fromJson(Map<String, dynamic> json) {
    return UserDTO(
      userID: json['userID'],
      username: json['username'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      password: json['password'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      birthDate: json['birthDate'] != null ? DateTime.parse(json['birthDate']) : null,
      gender: json['gender'],
      otp: json['otp'],
      otpExpiryTime: json['otpExpiryTime'] != null ? DateTime.parse(json['otpExpiryTime']) : null,
      canChangePassword: json['canChangePassword'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'userID': userID,
      'username': username,
      'email': email,
      'firstName': firstName,
      'lastName': lastName,
      'password': password,
      'createdAt': createdAt?.toIso8601String(),
      'birthDate': birthDate?.toIso8601String(),
      'gender': gender,
      'otp': otp,
      'otpExpiryTime': otpExpiryTime?.toIso8601String(),
      'canChangePassword': canChangePassword,
    };
  }

  @override
  String toString() {
    return jsonEncode(this.toJson());
  }
}
