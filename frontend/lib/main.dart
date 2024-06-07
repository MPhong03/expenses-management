import 'package:flutter/material.dart';

void main() {
  runApp(const StartupScreen());
}

class StartupScreen extends StatelessWidget {
  const StartupScreen({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Stack(
          children: [
            // Background Image
            Container(
              decoration: const BoxDecoration(
                image: DecorationImage(
                  image: AssetImage('assets/images/background-1.png'),
                  fit: BoxFit.cover,
                ),
              ),
            ),
            // Foreground Content
            Padding(
              padding: const EdgeInsets.only(bottom: 40.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.end,
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  const SizedBox(height: 20),
                  // App Name
                  const Text(
                    'ExpenseTracker',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.black,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 40),
                  // Login Button
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 40.0),
                    child: ElevatedButton(
                      onPressed: () {
                        // Handle login action
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.black, // Replace primary with backgroundColor
                        padding: const EdgeInsets.symmetric(vertical: 10),
                        textStyle: const TextStyle(fontSize: 16),
                      ),
                      child: const Text('Login'),
                    ),
                  ),
                  const SizedBox(height: 20),
                  // Register Button
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 40.0),
                    child: OutlinedButton(
                      onPressed: () {
                        // Handle register action
                      },
                      style: OutlinedButton.styleFrom(
                        foregroundColor: Colors.black, // Replace primary with foregroundColor
                        padding: const EdgeInsets.symmetric(vertical: 10),
                        textStyle: const TextStyle(fontSize: 16),
                      ),
                      child: const Text('Register'),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}