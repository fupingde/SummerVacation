package com.example.module.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0010H\u0014J\b\u0010\u0015\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/module/ui/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/module/main/databinding/ActivityMainBinding;", "dynamicFab", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "rotationAngle", "", "rotationHandler", "Landroid/os/Handler;", "rotationRunnable", "Ljava/lang/Runnable;", "songViewModel", "Lcom/example/module/ui/viewmodel/SongViewModel;", "observeSongData", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupDynamicFab", "module_main_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.module.main.databinding.ActivityMainBinding binding;
    private com.example.module.ui.viewmodel.SongViewModel songViewModel;
    private float rotationAngle = 0.0F;
    @org.jetbrains.annotations.NotNull
    private final android.os.Handler rotationHandler = null;
    private com.google.android.material.floatingactionbutton.FloatingActionButton dynamicFab;
    @org.jetbrains.annotations.NotNull
    private final java.lang.Runnable rotationRunnable = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupDynamicFab() {
    }
    
    private final void observeSongData() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}