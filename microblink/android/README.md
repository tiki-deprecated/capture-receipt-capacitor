# Publish Microblink Android libs to GitHub Packages

1) Set version (`build.gradle`)
```groovy
ext {
    microblinkVersion = "1.6.7"
}
```
2) Clean for good measure
```shell
./gradlew clean
```
3) Clone microblink repository
```shell
./gradlew clone
```
4) Publish libs to GitHub packages
```shell
./gradlew publish
```
