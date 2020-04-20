package io.airlift.configuration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

public class FileExistsValidator implements ConstraintValidator<FileExists, Object> {
    @Override
    public void initialize(FileExists ignored) {
        // Annotation has no properties
    }

    @Override
    public boolean isValid(Object path, ConstraintValidatorContext context) {
        if (path == null) {
            return true;
        }

        if (path instanceof String) {
            return Files.exists(Paths.get((String) path));
        }

        if (path instanceof Path) {
            return ((Path) path).toFile().exists();
        }

        if (path instanceof File) {
            return ((File) path).exists();
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(format("could not validate value of type %s", path.getClass().getSimpleName()))
        .addConstraintViolation();

        return false;
    }
}