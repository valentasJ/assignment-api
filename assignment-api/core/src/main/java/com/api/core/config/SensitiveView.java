package com.api.core.config;

public class SensitiveView {
    public static class Public {}   // Public view, available to everyone
    public static class Admin extends Public {}  // Admin view, which includes sensitive data
}