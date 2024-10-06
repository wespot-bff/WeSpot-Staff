package bff.wespot.staff.data.di

import org.koin.core.module.Module
import kotlin.reflect.KClass

expect val dataModule: Module

public interface Repositories {
    public val map: Map<KClass<*>, Any>
}

public class DefaultRepositories(
    public override val map: Map<KClass<*>, Any>,
) : Repositories
