package com.example.Network.Bean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b^\b\u0086\b\u0018\u00002\u00020\u0001B\u00f5\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0001\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\u000b\u0012\u0006\u0010\u0010\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\r\u0012\u0006\u0010\u0012\u001a\u00020\u000b\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u0005\u0012\u0006\u0010\u0015\u001a\u00020\r\u0012\u0006\u0010\u0016\u001a\u00020\r\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0001\u0012\u0006\u0010\u0019\u001a\u00020\u0001\u0012\u0006\u0010\u001a\u001a\u00020\u0005\u0012\u0006\u0010\u001b\u001a\u00020\u0003\u0012\u0006\u0010\u001c\u001a\u00020\u0005\u0012\u0006\u0010\u001d\u001a\u00020\r\u0012\u0006\u0010\u001e\u001a\u00020\u0003\u0012\u0006\u0010\u001f\u001a\u00020\u0001\u0012\u0006\u0010 \u001a\u00020\r\u0012\u0006\u0010!\u001a\u00020\u000b\u0012\u0006\u0010\"\u001a\u00020\u0003\u0012\u0006\u0010#\u001a\u00020\u0003\u00a2\u0006\u0002\u0010$J\t\u0010H\u001a\u00020\u0003H\u00c6\u0003J\t\u0010I\u001a\u00020\u000bH\u00c6\u0003J\t\u0010J\u001a\u00020\rH\u00c6\u0003J\t\u0010K\u001a\u00020\rH\u00c6\u0003J\t\u0010L\u001a\u00020\u000bH\u00c6\u0003J\t\u0010M\u001a\u00020\u0003H\u00c6\u0003J\t\u0010N\u001a\u00020\u0005H\u00c6\u0003J\t\u0010O\u001a\u00020\rH\u00c6\u0003J\t\u0010P\u001a\u00020\rH\u00c6\u0003J\t\u0010Q\u001a\u00020\u0003H\u00c6\u0003J\t\u0010R\u001a\u00020\u0001H\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\t\u0010T\u001a\u00020\u0001H\u00c6\u0003J\t\u0010U\u001a\u00020\u0005H\u00c6\u0003J\t\u0010V\u001a\u00020\u0003H\u00c6\u0003J\t\u0010W\u001a\u00020\u0005H\u00c6\u0003J\t\u0010X\u001a\u00020\rH\u00c6\u0003J\t\u0010Y\u001a\u00020\u0003H\u00c6\u0003J\t\u0010Z\u001a\u00020\u0001H\u00c6\u0003J\t\u0010[\u001a\u00020\rH\u00c6\u0003J\t\u0010\\\u001a\u00020\u000bH\u00c6\u0003J\t\u0010]\u001a\u00020\u0003H\u00c6\u0003J\t\u0010^\u001a\u00020\u0003H\u00c6\u0003J\t\u0010_\u001a\u00020\u0003H\u00c6\u0003J\t\u0010`\u001a\u00020\u0003H\u00c6\u0003J\t\u0010a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010b\u001a\u00020\u0001H\u00c6\u0003J\t\u0010c\u001a\u00020\u000bH\u00c6\u0003J\t\u0010d\u001a\u00020\rH\u00c6\u0003J\t\u0010e\u001a\u00020\rH\u00c6\u0003J\u00b5\u0002\u0010f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\u000b2\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00012\b\b\u0002\u0010\u0019\u001a\u00020\u00012\b\b\u0002\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00052\b\b\u0002\u0010\u001d\u001a\u00020\r2\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u00012\b\b\u0002\u0010 \u001a\u00020\r2\b\b\u0002\u0010!\u001a\u00020\u000b2\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010g\u001a\u00020\u00052\b\u0010h\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010i\u001a\u00020\u0003H\u00d6\u0001J\t\u0010j\u001a\u00020\rH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010&R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010&R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010&R\u0011\u0010\t\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u000e\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00101R\u0011\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010/R\u0011\u0010\u0010\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00101R\u0011\u0010\u0011\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00101R\u0011\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010/R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010&R\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010(R\u0011\u0010\u0015\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u00101R\u0011\u0010\u0016\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u00101R\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010&R\u0011\u0010\u0018\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010-R\u0011\u0010\u0019\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010-R\u0011\u0010\u001a\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010(R\u0011\u0010\u001b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010&R\u0011\u0010\u001c\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010(R\u0011\u0010\u001d\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u00101R\u0011\u0010\u001e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010&R\u0011\u0010\u001f\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010-R\u0011\u0010 \u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u00101R\u0011\u0010!\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010/R\u0011\u0010\"\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010&R\u0011\u0010#\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010&\u00a8\u0006k"}, d2 = {"Lcom/example/Network/Bean/Subscriber;", "", "accountStatus", "", "anchor", "", "authStatus", "authenticationTypes", "authority", "avatarDetail", "avatarImgId", "", "avatarImgIdStr", "", "avatarUrl", "backgroundImgId", "backgroundImgIdStr", "backgroundUrl", "birthday", "city", "defaultAvatar", "description", "detailDescription", "djStatus", "expertTags", "experts", "followed", "gender", "mutual", "nickname", "province", "remarkName", "signature", "userId", "userType", "vipType", "(IZIIILjava/lang/Object;JLjava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;JIZLjava/lang/String;Ljava/lang/String;ILjava/lang/Object;Ljava/lang/Object;ZIZLjava/lang/String;ILjava/lang/Object;Ljava/lang/String;JII)V", "getAccountStatus", "()I", "getAnchor", "()Z", "getAuthStatus", "getAuthenticationTypes", "getAuthority", "getAvatarDetail", "()Ljava/lang/Object;", "getAvatarImgId", "()J", "getAvatarImgIdStr", "()Ljava/lang/String;", "getAvatarUrl", "getBackgroundImgId", "getBackgroundImgIdStr", "getBackgroundUrl", "getBirthday", "getCity", "getDefaultAvatar", "getDescription", "getDetailDescription", "getDjStatus", "getExpertTags", "getExperts", "getFollowed", "getGender", "getMutual", "getNickname", "getProvince", "getRemarkName", "getSignature", "getUserId", "getUserType", "getVipType", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "module_main_debug"})
public final class Subscriber {
    private final int accountStatus = 0;
    private final boolean anchor = false;
    private final int authStatus = 0;
    private final int authenticationTypes = 0;
    private final int authority = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.Object avatarDetail = null;
    private final long avatarImgId = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String avatarImgIdStr = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String avatarUrl = null;
    private final long backgroundImgId = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String backgroundImgIdStr = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String backgroundUrl = null;
    private final long birthday = 0L;
    private final int city = 0;
    private final boolean defaultAvatar = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String detailDescription = null;
    private final int djStatus = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.Object expertTags = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.Object experts = null;
    private final boolean followed = false;
    private final int gender = 0;
    private final boolean mutual = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String nickname = null;
    private final int province = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.Object remarkName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String signature = null;
    private final long userId = 0L;
    private final int userType = 0;
    private final int vipType = 0;
    
    public Subscriber(int accountStatus, boolean anchor, int authStatus, int authenticationTypes, int authority, @org.jetbrains.annotations.NotNull
    java.lang.Object avatarDetail, long avatarImgId, @org.jetbrains.annotations.NotNull
    java.lang.String avatarImgIdStr, @org.jetbrains.annotations.NotNull
    java.lang.String avatarUrl, long backgroundImgId, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundImgIdStr, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundUrl, long birthday, int city, boolean defaultAvatar, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String detailDescription, int djStatus, @org.jetbrains.annotations.NotNull
    java.lang.Object expertTags, @org.jetbrains.annotations.NotNull
    java.lang.Object experts, boolean followed, int gender, boolean mutual, @org.jetbrains.annotations.NotNull
    java.lang.String nickname, int province, @org.jetbrains.annotations.NotNull
    java.lang.Object remarkName, @org.jetbrains.annotations.NotNull
    java.lang.String signature, long userId, int userType, int vipType) {
        super();
    }
    
    public final int getAccountStatus() {
        return 0;
    }
    
    public final boolean getAnchor() {
        return false;
    }
    
    public final int getAuthStatus() {
        return 0;
    }
    
    public final int getAuthenticationTypes() {
        return 0;
    }
    
    public final int getAuthority() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object getAvatarDetail() {
        return null;
    }
    
    public final long getAvatarImgId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAvatarImgIdStr() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAvatarUrl() {
        return null;
    }
    
    public final long getBackgroundImgId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBackgroundImgIdStr() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getBackgroundUrl() {
        return null;
    }
    
    public final long getBirthday() {
        return 0L;
    }
    
    public final int getCity() {
        return 0;
    }
    
    public final boolean getDefaultAvatar() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDetailDescription() {
        return null;
    }
    
    public final int getDjStatus() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object getExpertTags() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object getExperts() {
        return null;
    }
    
    public final boolean getFollowed() {
        return false;
    }
    
    public final int getGender() {
        return 0;
    }
    
    public final boolean getMutual() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNickname() {
        return null;
    }
    
    public final int getProvince() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object getRemarkName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSignature() {
        return null;
    }
    
    public final long getUserId() {
        return 0L;
    }
    
    public final int getUserType() {
        return 0;
    }
    
    public final int getVipType() {
        return 0;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final long component10() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component17() {
        return null;
    }
    
    public final int component18() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object component19() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object component20() {
        return null;
    }
    
    public final boolean component21() {
        return false;
    }
    
    public final int component22() {
        return 0;
    }
    
    public final boolean component23() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component24() {
        return null;
    }
    
    public final int component25() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component27() {
        return null;
    }
    
    public final long component28() {
        return 0L;
    }
    
    public final int component29() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component30() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.Object component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.Network.Bean.Subscriber copy(int accountStatus, boolean anchor, int authStatus, int authenticationTypes, int authority, @org.jetbrains.annotations.NotNull
    java.lang.Object avatarDetail, long avatarImgId, @org.jetbrains.annotations.NotNull
    java.lang.String avatarImgIdStr, @org.jetbrains.annotations.NotNull
    java.lang.String avatarUrl, long backgroundImgId, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundImgIdStr, @org.jetbrains.annotations.NotNull
    java.lang.String backgroundUrl, long birthday, int city, boolean defaultAvatar, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String detailDescription, int djStatus, @org.jetbrains.annotations.NotNull
    java.lang.Object expertTags, @org.jetbrains.annotations.NotNull
    java.lang.Object experts, boolean followed, int gender, boolean mutual, @org.jetbrains.annotations.NotNull
    java.lang.String nickname, int province, @org.jetbrains.annotations.NotNull
    java.lang.Object remarkName, @org.jetbrains.annotations.NotNull
    java.lang.String signature, long userId, int userType, int vipType) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}